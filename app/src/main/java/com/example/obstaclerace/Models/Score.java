package com.example.obstaclerace.Models;

public class Score {
        private int record = 0;
        private String image = "";

        private double CoordinateX = 0;
        private double CoordinateY = 0;
        public Score() {}

        public String getUserNameAndScore() {
            return " " + record;
        }



        public String getImage() {
           return image;
         }

        public Score setImage(String image) {
            this.image = image;
            return this;
        }

    public Score setCoordinateX(double coordinateX) {
        CoordinateX = coordinateX;
        return this;
    }

    public Score setCoordinateY(double coordinateY) {
        CoordinateY = coordinateY;
        return this;
    }

    public double getCoordinateX() {
        return CoordinateX;
    }

    public double getCoordinateY() {
        return CoordinateY;
    }

    public int getRecord() {
             return record;
             }

            public Score setRecord(int record) {
                this.record = record;
                return this;
            }


        @Override
        public String toString() {
            return "Score{" +
                    "record" + record + '\''+
                    ", image='" + image + '\'' +
                    '}';
        }
    }

