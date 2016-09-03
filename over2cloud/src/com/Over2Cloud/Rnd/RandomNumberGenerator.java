package com.Over2Cloud.Rnd;

public class RandomNumberGenerator
{

        /**
         * This method returns a random number between 0 and maxLimit.
         */
        public int getRandomNumber(int maxLimit)
        {
                return (int) (Math.random() * maxLimit);
        }

        /**
         * This method returns a random number between smallest n digit number and largest n digit number. For example if
         * n=4, then a random number would be generated between 1000 and 9999.
         */
        public int getNDigitRandomNumber(int n)
        {
                int upperLimit = getMaxNDigitNumber(n);
                int lowerLimit = getMinNDigitNumber(n);
                int s = getRandomNumber(upperLimit);
                if (s < lowerLimit)
                {
                        s += lowerLimit;
                }
                return s;
        }

        /**
         * This method returns a random number between lowerLimit and upperLimit.
         */
        public int getRandomNumber(int lowerLimit, int upperLimit)
        {
                int v = (int) (Math.random() * upperLimit);
                if (v < lowerLimit)
                {
                        v += lowerLimit;
                }
                return v;
        }

        /**
         * This method returns a largest n digit number. The value returned depends on integer limit.
         */
        private int getMaxNDigitNumber(int n)
        {
                int s = 0;
                int j = 10;
                for (int i = 0; i < n; i++)
                {
                        int m = 9;
                        s = (s * j) + m;
                }
                return s;
        }

        /**
         * This method returns a lowest n digit number. The value returned depends on integer limit.
         */
        private int getMinNDigitNumber(int n)
        {
                int s = 0;
                int j = 10;
                for (int i = 0; i < n - 1; i++)
                {
                        int m = 9;
                        s = (s * j) + m;
                }
                return s + 1;
        }

        public static void main(String[] args)
        {
                RandomNumberGenerator rg = new RandomNumberGenerator();
                System.out.println("Random number between 10 and 20 -> " + rg.getRandomNumber(10, 20));
                System.out.println("Random number less than 8000 -> " + rg.getRandomNumber(8000));
                System.out.println("Random number with 4 digits -> " + rg.getNDigitRandomNumber(8));
        }
}