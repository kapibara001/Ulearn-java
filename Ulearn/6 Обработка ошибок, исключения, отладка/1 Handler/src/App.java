public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public class Handler {
        public int handleResults(String input) {
            try {
                if (input.contains("error")) {
                    throw new HandlerException("Строка содержит 'error'");
                } else if (input == null || input.isEmpty()) {
                    throw  new NullHandlerException("Строка пуста или равна null");
                }

                String[] parts = input.split("\\.");

                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                int c = Integer.parseInt(parts[2]);
                int d = Integer.parseInt(parts[3]);

                int result = (a+b) - (c*d);

                if (result < 0) {
                    throw new HandlerResultException("Результат меньше 0");
                }

                return result;

            } catch (HandlerException | HandlerResultException | NullHandlerException e) {
                throw e;
            } catch (Exception e) {
                return 0;
            }
        }
        
        class HandlerException extends RuntimeException {
            public HandlerException(String message) {
                super(message);
            }
        }

        class HandlerResultException  extends RuntimeException {
            public HandlerResultException(String message) {
                super(message);
            }
        }

        class NullHandlerException extends RuntimeException {
            public NullHandlerException(String message) {
                super(message);
            }
        }
    }
}