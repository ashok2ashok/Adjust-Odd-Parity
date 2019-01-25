package im.ashok.parity;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        if(args!=null && args.length==1){
            System.out.println("--Key String before adjusting Parity bits: \n"+args[0]);
            System.out.println("\n--Binary Bits before adjusting Parity bits: ");
            for (byte b : args[0].getBytes()) {
                System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
            }

            System.out.println("\n----Applying Odd Parity Bits-----");
            byte[] adjustedBytes = adjustDESParity(args[0].getBytes());

            System.out.println("****--Completed application of Odd Parity Bits--****");
            System.out.println("\nVerifying if Odd Parity has been applied: "+isDESParityAdjusted(adjustedBytes));
            System.out.println("\n--Binary Bits after adjusting Parity bits: ");
            for (byte b : adjustedBytes) {
                System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
            }
            System.out.println("\n--Key String after adjusting Parity bits: \n"+new String(adjustedBytes));
        }
        else{
            System.out.println("Please pass the Key as a runtime argument to adjust for Odd Parity...\n");
            System.out.println("--Sample implementation is printed below:\n\n");
            main(new String[]{"d8732a6681e7133e545f100d8db9c61ad8732a6681e7133e"});
            System.out.println("\n *** Above is for ILLUSTRATION Purposes ONLY and not for actual input ****");
        }
    }

    /**
     * DES Keys use the LSB as the odd parity bit.  This method can
     * be used enforce correct parity.
     * Simply manipulate the last bit of each byte to make it odd parity, for example:
     *  - binary value of the number 27 yields 0001 1011 even parity and, by flipping the low order bit (to 0), this makes it odd parity, yielding a binary value of 26.
     *  - binary value of the number 28 yields 0001 1100 thereby already odd parity.
     * @param bytes the byte array to set the odd parity on.
     */
    public static byte[] adjustDESParity (byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = (byte)((b & 0xfe) | ((((b >> 1) ^ (b >> 2) ^ (b >> 3) ^ (b >> 4) ^ (b >> 5) ^ (b >> 6) ^ (b >> 7)) ^ 0x01) & 0x01));
        }
        return bytes;
    }

    /**
     * DES Keys use the LSB as the odd parity bit.  This method checks
     * whether the parity is adjusted or not
     *
     * @param bytes the byte[] to be checked
     * @return true if parity is adjusted else returns false
     */
    public static boolean isDESParityAdjusted (byte[] bytes) {
        byte[] correct = (byte[])bytes.clone();
        adjustDESParity(correct);
        return  Arrays.equals(bytes, correct);
    }
}
