package Power_Problem;

import java.util.Stack;

/**
 * this program calculates a power in complexity lower then O(n).
 * first algorithm - we take the pow and convert it into binary by dividing by two and enter the remainder into stack, afterwords
 * we pull out the stack content which finally gives us the binary presentation of the pow.
 * then we convert the binary string to X^n - for instance if we got X^11 so 11 will be 1011 now we know we have to calculate x^8 *x^2 *x^1 = x^11.
 * so log n for the convert to binary , log n for reading from stack , log n for converting again to x^n and another log n for the multiple.
 * so total 4(log n).
 * second algorithm we want to improve so we will relinquish on converting to binary and we will do it simultaneously which means -
 * we divide and every divide if the number is odd (which means we have a reminder) and we need to multiple. so total O(log n) .
 */

public class Main {

    public static void main(String[] args){
        long ans = power(3,12);
        System.out.println(ans);
        long ans2 = power2(3,12);
        System.out.println(ans2);
    }

    public static long power(double base,int pow){
        Stack<Integer> binaryStack = new Stack<>();
        long sum =1;
        int twoMultiple =1;
        int temp = pow,remainder=0;
        String binaryDisplay="";
        // first convert the pow into binary.
        while(temp>0){
            remainder = temp%2;
            temp/=2;
            binaryStack.push(remainder); // at the end when we pull out the numbers we will have the pow in binary display.
        }

        while(!binaryStack.isEmpty()){ // as long as the stack isnt empty , pull
            binaryDisplay+=binaryStack.pop();
        }

        // now we have display of pow in binary, iterate through the length of it to find and calculate X^? and after it multiple
        // for instance 3^11 its 3^8 * 3^2 * 3^1
        for(int i=binaryDisplay.length()-1;i>=0;i--){
            if(binaryDisplay.charAt(i)=='1'){
                sum*=calcultePower(base,twoMultiple);

            }
            twoMultiple*=2;
        }
        return sum;

    } // 4(log n)

    public static long calcultePower(double base,int twoMultiple){
        int powStart=1;
        long ans = (long) base;
        while(powStart<twoMultiple){
            ans*=ans;
            powStart*=2;
        }
      return ans;

    }

    public static long power2(double base,int pow){ //O(log n)

        long ans =1; // initializing the multiples.

        while(pow!=0){
            if(pow%2!=0){ // if pow is odd
                ans= (long) (ans*base);
            }
            pow/=2;
            base*=base;
        }
        return ans;
    }

}

