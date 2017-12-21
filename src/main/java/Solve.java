import java.util.ArrayList;

/**
 * Класс, в котором реализовано все для нахождения ответа.
 */
public class Solve {
    /**
     * Элементы арифметического выражения.
     */
    final private String elements[];
    /**
     * Число пропущенный знаков арифметических операций.
     */
    final private int numOp;

    /**
     * Конструктор.
     * @param str Строка с арифметическим выражением, в котором пропущены операторы.
     * @throws Exception Если что-то пошло не так, то выброситься исключение.
     */
    public Solve(String str) throws Exception {
        /**
         * ArrayList, в который складываем элементы арифметического выражения.
         */
        ArrayList<String> elements = new ArrayList<String>();
        /**
         * Число пропущенный знаков арифметических операций.
         */
        int numOp = 0;

        /**
         * Последний добавленный элемент арифметического выражения.
         */
        String last = null;


        /**
         * Номер рассматриваемого символа в строке.
         */
        int i = 0;
        while (i < str.length()) {
            /**
             * i-ый символ строки, далее называем его текущим символом.
             */
            char ch = str.charAt(i);

            //Если текущий символ - это пробел, то перехожим к следующему символу.
            if (ch == ' ') {
                ++i;
                continue;
            }

            /**
             * Текущий добавляемый элемент арифметического выражения.
             */
            String act = null;

            //Если текущий символ - это скобка или знак равно.
            if (ch == '(' || ch == ')' || ch == '=') {
                //Преобразовываем символ к строке.
                act = Character.toString(ch);
                //Переходим к следующему символу.
                ++i;
            } else if (Character.isDigit(ch)){ //Если текущий символ - это цифра.
                /**
                 * Номер первого символа в строке после числа.
                 */
                int j = i;
                //С помощью цикла находим номер первого символа в строке после числа.
                while (j < str.length() && Character.isDigit(str.charAt(j))) ++j;
                //Вычленяем число из строки.
                act = str.substring(i, j);
                //Переходим к символу, который идет после числа.
                i = j;
            } else {
                //В остальных случаях бросаем исключение.
                throw new Exception("Bad string.");
            }


            //Если предыдущий элемент арифметического выражения - это корректный левый операнд,
            if ((last != null)
                    && !last.equals("(")
                    && !act.equals(")")
                    && !last.equals("=")
                    && !act.equals("=")) {

                //Добавляем знак вопроса, для указания того, что здесь нужно вставить арифметическую операцию.
                elements.add("?");
                ++numOp;
            }

            //Добавляем текущий элемент к выражению.
            elements.add(act);
            last = act;
        }
        //Задаем значение полей класса.
        this.numOp = numOp;
        this.elements = elements.toArray(new String[0]);
    }

    /**
     * Проверка результата выражения при заданных операциях.
     * @param operations операции.
     * @return Элементы выражения.
     *          null, если после подстановки операторов значение выражения не сошлось.
     */
    private String[] check(String operations[]) {
        //Проверка: что кол-во операций равно необходимому числу.
        if (operations.length != numOp) return null;

        /**
         * Копия исходного выражения, в котором знаки вопросов заменены операциями.
         */
        String subElemets[] = new String[elements.length];
        /**
         * В цикле знаки вопроса заменяем на операции.
         */
        for (int i = 0, j = 0; i < elements.length; ++i) {
            if (elements[i].equals("?")) {
                subElemets[i] = operations[j++];
            } else {
                subElemets[i] = elements[i];
            }
        }

        /**
         * Номер элемента со знаком "=".
         */
        int posEqual = 0;
        //Поиск номера элемента выражения со знаком "=".
        while (posEqual < subElemets.length && !subElemets[posEqual].equals("=")) {
            ++posEqual;
        }
        //Если в выражении нет символа "=", то ответа нет.
        if (posEqual >= subElemets.length - 1) return null;

        try {
            //Проверка, что левая и правая часть от знака "=" совпдают.
            if (calc(subElemets, 0, posEqual - 1) == calc(subElemets, posEqual + 1, subElemets.length - 1)) {
                //Если левая и права часть равны, то возвращаем элементы выражения.
                return subElemets;
            } else {
                //Если нет, то возвращаем null.
                return null;
            }
        } catch (Exception exp) {
            //Если во время проверки, возникло исключение, то возвращаем null.
            return null;
        }
    }

    /**
     * Вычисление значения арифметического выражения.
     * @param parts элементы выражения.
     * @param left номер элемента с которого начинаем вычислять.
     * @param right номер элемента до которого вычисляем.
     * @return значение выражения.
     * @throws Exception Если что-то пошло не так, то выброситься исключение.
     */
    private long calc(String parts[], int left, int right) throws Exception {
        if (left > right) throw new Exception("Left large right.");

        /**
         * Цикл по приоритету операций:
         * - Нулевой приоритет у + и -.
         * - Единичный приоритет у *.
         */
        for (int type = 0; type < 2; ++type) {
            /**
             * Баланс скобок. Если эта переменная не равна нулю, то в текущий
             * момент мы находимся внутри скобки.
             */
            int balance = 0;
            for (int i = right; i >= left; --i) {
                /**
                 * Элемент выражения.
                 */
                String part = parts[i];

                if (part.equals("(")) ++balance;
                else if (part.equals(")")) --balance;
                else if (balance == 0) {
                    /**
                     * Если можем выполнить операцию + или -.
                     */
                    boolean type0 = (part.equals("+") || part.equals("-")) && (type == 0);
                    /**
                     * Если можем выполнить операцию *.
                     */
                    boolean type1 = part.equals("*") && (type == 1);

                    /**
                     * Если можем выполнить какую-нибудь операцию, то...
                     */
                    if (type0 || type1) {
                        /**
                         * Вычисляем значение левого и правого операнда.
                         */
                        long resLeft = calc(parts, left, i - 1);
                        long resRight = calc(parts, i + 1, right);

                        /**
                         * Вычисляем значение операции.
                         */
                        if (part.equals("+")) return resLeft + resRight;
                        if (part.equals("-")) return resLeft - resRight;
                        if (part.equals("*")) return resLeft * resRight;
                    }
                }
            }
        }


        int balance = 0;
        for (int i = left + 1; i < right; ++i) {
            if (parts[i].equals("(")) ++balance;
            else if (parts[i].equals(")")) --balance;
        }

        /**
         * Проверка случая, когда все выражение взято в круглые скобки.
         */
        if (balance == 0 && parts[left].equals("(") && parts[right].equals(")")) {
            return calc(parts, left + 1, right - 1);
        }

        /**
         * Разбор случая, когда элемент арифметического выражения это число.
         */
        if (left == right) return Long.parseLong(parts[left]);

        /**
         * В остальных случаях генерируем исключение.
         */
        throw new Exception("End calc.");
    }

    /**
     * Поиск ответа.
     * @return строка с ответом на задачу, если ответа нет, то null.
     */
    public String solve() {
        /**
         * Для перебора всех вариантов подстановки знаков в арифметическое выражение
         * необходимо перебрать все числа в троичной системе счисление длиной не более numOp.
         * Эта идея взята из одного задания ЕГЭ:)
         */

        /**
         * Число до которого перебираем.
         */
        int max = (int) Math.pow(3, numOp);
        /**
         * Массив с операциями.
         */
        String ops[] = new String[numOp];

        /**
         * Перебор всех вариантов.
         */
        for (int mask = 0; mask < max; ++mask) {
            int m = mask;
            /**
             * Переводим число m в троичную систему счисления и сразу же по цифрам определяем какую операцию ставить.
             */
            for (int i = 0; i < numOp; ++i) {
                switch (m % 3) {
                    case 0: ops[i] = "+"; break;
                    case 1: ops[i] = "-"; break;
                    case 2: ops[i] = "*"; break;
                }
                m /= 3;
            }
            /**
             * Проверка выражения.
             */
            String ans[] = check(ops);
            /**
             * Если при подстановке операций выражение сошлось, то...
             */
            if (ans != null) {
                /**
                 * Собираем выражение в строку.
                 */
                StringBuilder sb = new StringBuilder("");
                for (String s : ans) {
                    sb.append(s);
                }
                /**
                 * И возвращаем эту строку.
                 */
                return sb.toString();
            }
        }
        return null;
    }
}
