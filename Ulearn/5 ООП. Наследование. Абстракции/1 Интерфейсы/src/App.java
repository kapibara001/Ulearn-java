public class App {
    interface TimeUnit {
        long toMillis();
        long toSeconds();
        long toMinutes();
        long toHours();
    }

    // Milliseconds
    class Milliseconds implements TimeUnit {
        private final long value;

        public Milliseconds(long value) { this.value = value; }

        public long getValue() { return value; }

        @Override
        public long toMillis() {
            return value;
        }

        @Override
        public long toSeconds() {
            return Math.round((double) value / 1000);
        }

        @Override
        public long toMinutes() {
            return Math.round((double) value / (1000 * 60));
        }

        @Override
        public long toHours() {
            return Math.round((double) value / (1000 * 60 * 60));
        }
    }

    // Seconds
    class Seconds implements TimeUnit {
        private final long value;

        public Seconds(long value) { this.value = value; }

        public long getValue() { return value; }

        @Override
        public long toMillis() {
            return value * 1000;
        }

        @Override
        public long toSeconds() {
            return value;
        }

        @Override
        public long toMinutes() {
            return Math.round((double) value / 60);
        }

        @Override
        public long toHours() {
            return Math.round((double) value / 3600);
        }
    }

    // Minutes
    class Minutes implements TimeUnit {
        private final long value;

        public Minutes(long value) { this.value = value; }

        public long getValue() { return value; }

        @Override
        public long toMillis() {
            return value * 60 * 1000;
        }

        @Override
        public long toSeconds() {
            return value * 60;
        }

        @Override
        public long toMinutes() {
            return value;
        }

        @Override
        public long toHours() {
            return Math.round((double) value / 60);
        }
    }

    // Hours
    class Hours implements TimeUnit {
        private final long value;

        public Hours(long value) { this.value = value; }

        public long getValue() { return value; }

        @Override
        public long toMillis() {
            return value * 60 * 60 * 1000;
        }

        @Override
        public long toSeconds() {
            return value * 3600;
        }

        @Override
        public long toMinutes() {
            return value * 60;
        }

        @Override
        public long toHours() {
            return value;
        }
    }
}
