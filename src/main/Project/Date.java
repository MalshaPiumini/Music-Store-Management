package Project;

import java.util.Scanner;

public class Date {
        private int date;
        private int year;
        private int month;

        public Date(int date,int year, int month,int second,int hours){
            this.date=date;
            this.year=year;
            this.month=month;
        }
        public Date(){

        }

        public void setYear(int year) {
            while (year<1990 ||year>2019 ){
                System.out.println("Not a valid year, re enter month");
                Scanner in=new Scanner(System.in);
                year=in.nextInt();
            }

                this.year = year;
        }

        public void setMonth(int month) {

            while(month>12||month <0){
                System.out.println("Not a valid month, re enter month");
                Scanner in=new Scanner(System.in);
                month=in.nextInt();
            }
                this.month = month;

        }

        public void setDate(int date) {
            while (date<0){
                System.out.println("Not a valid date, re enter date");
                Scanner in=new Scanner(System.in);
                Main.validateInteger();
                date=in.nextInt();
            }

            if((this.month==1|| this.month==3 || this.month==5 || this.month==7 || this.month==8 || this.month==10 || this.month==12)  && date<=30){
                this.date=date;
            }else if((this.month==4|| this.month==6 || this.month==9 || this.month==11 || this.month==8 )  && date<=31){
                this.date=date;
            }else if(this.month==2 && date<=29){
                this.date=date;
            }else{
                System.out.println("Not valid");
            }

        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDate() {
            return date;
        }
    }


