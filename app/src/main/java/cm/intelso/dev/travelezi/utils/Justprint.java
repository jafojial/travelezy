package cm.intelso.dev.travelezi.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.util.Date;

import justtide.ThermalPrinter;

/**
 * Created by JAFOJIAL on 20/06/2016.
 */
public class Justprint {

    private String stateString = "";
    private String ticketString = "";
    private byte[] buffer;
    private int ilevel = 6;

    // check if /system/lib/libposprinter.so exist
    public boolean isJustprinter(){
        File file = new File("/system/lib/libposprinter.so");
        /*
        if (!file.canRead()) {
            return false;
        }
        */
        if (!file.isFile()) {
            Log.e("File checking", "Source File not exist : /system/lib/libposprinter.so");
            return false;
        }else {
            Log.e("File checking", "file exist");
            return true;
        }
        //return false;
    }

    public int printTicket(Bitmap bitmap, ThermalPrinter thermalPrinter, String facture)
    {
        int i,state;
        int ret = -1;
        //ThermalPrinter thermalPrinter = ThermalPrinter.getInstance();

        thermalPrinter.initBuffer();
        //Logo printing
        thermalPrinter.setGray(ilevel);
        /*
        Resources res= context.getResources();
        //Bitmap bitmap1 =  BitmapFactory.decodeResource(res, R.drawable.justtide1);//getResources().openRawResource(R.raw.payway)
        Bitmap bitmap1 =  BitmapFactory.decodeResource(res, R.raw.payway);
        thermalPrinter.printLogo(0,0,bitmap1);
        */
        thermalPrinter.printLogo(0,0,bitmap);
        thermalPrinter.setStep(100);
        //thermalPrinter.printStart();
        //state = thermalPrinter.waitForPrintFinish();
        //bLogo = false;
        //return state;
        //End of logo printing

        //thermalPrinter.setGray(ilevel);
        thermalPrinter.setHeightAndLeft(0, 0);
        thermalPrinter.setLineSpacing(5);
        thermalPrinter.setDispMode(ThermalPrinter.UMODE);
        //ret = thermalPrinter.setFont(ThermalPrinter.ASC8X16_DEF,ThermalPrinter.HZK24F);
        thermalPrinter.setFont(ThermalPrinter.ASC12X24, ThermalPrinter.HZK12);
        ret =thermalPrinter.getFontCH();
        Log.i("Print", "setFontCH:" + ret);

        thermalPrinter.print("DATE : " + new Date()+"\n");
        thermalPrinter.print(facture);
        thermalPrinter.setDispMode(ThermalPrinter.CMODE);
        thermalPrinter.setFont(ThermalPrinter.ASC12X24B, ThermalPrinter.HZK12);
        ret =thermalPrinter.getFontCH();
        Log.i("Print", "setFontCH:" + ret);

        thermalPrinter.setStep(20);
        thermalPrinter.shiftRight(200);
        thermalPrinter.print("\nThank you!\n");
        /*
        ticketString += "POS SALES SLIP\n";
        thermalPrinter.setFont(ThermalPrinter.ASC8X16_DEF,ThermalPrinter.HZK12);
        thermalPrinter.print("MERCHANT COPY\n");
        ticketString += "MERCHANT COPY\n";
        for(i=0;i<27;i++) {
            buffer[i]= (byte) 0xfc;
        }
        ticketString += "___________________________\n";
        thermalPrinter.printLine(buffer);
        thermalPrinter.printLine(buffer);
        thermalPrinter.setStep(4);
        //System.out.println("setStep:"+ret);
        thermalPrinter.setFont(ThermalPrinter.ASC12X24,ThermalPrinter.HZK12);
        thermalPrinter.print("Custom name：\n");
        //thermalPrinter.print("(MERCHANT NAME):\n");
        ticketString += "(MERCHANT NAME):\n";
        thermalPrinter.print("(MERCHANT NO):\n");
        ticketString += "(MERCHANT NO):\n";
        thermalPrinter.shiftRight(100);
        thermalPrinter.print("001420183990573\n");
        ticketString += "001420183990573\n";
        thermalPrinter.print("(TERMINAL NO):00026715\n");
        ticketString += "(TERMINAL NO):00026715\n";
        thermalPrinter.print("(CARD NO):\n");
        ticketString += "(CARD NO):\n";
        thermalPrinter.shiftRight(60);
        thermalPrinter.print("955880******9503920\n");
        ticketString += "955880******9503920\n";
        thermalPrinter.print("(ACQUIRER):03050011\n");
        ticketString += "(ACQUIRER):03050011\n";
        thermalPrinter.print("(TXN TYPE): SALE\n");
        ticketString += "(TXN TYPE): SALE\n";
        thermalPrinter.print("(BATCH NO)  :000023\n");
        ticketString += "(BATCH NO)  :000023\n";
        thermalPrinter.print("(VOUCHER NO):000018\n");
        ticketString += "(VOUCHER NO):000018\n";
        thermalPrinter.print("(AUTH NO)   :987654\n");
        ticketString += "(AUTH NO)   :987654\n";
        thermalPrinter.print("(DATE/TIME):\n");
        ticketString += "(DATE/TIME):\n";
        thermalPrinter.shiftRight(80);
        thermalPrinter.print("2012/02/10 10:14:39\n");
        ticketString += "2012/02/10 10:14:39\n";
        thermalPrinter.print("(REF  NO):201202100015\n");
        ticketString += "(REF  NO):201202100015\n";
        thermalPrinter.print("(AMOUNT)      RMB:2.55\n");
        ticketString += "(AMOUNT)      RMB:2.55\n";
        thermalPrinter.setStep(12);
        for(i=0;i<48;i++)
            buffer[i]=(byte) 0xfc;
        ticketString += "_____________________________________\n";
        thermalPrinter.printLine(buffer);
        thermalPrinter.printLine(buffer);
        thermalPrinter.setStep(12);
        thermalPrinter.print("REFERENCE\n");
        ticketString += "REFERENCE\n";
        thermalPrinter.setStep(12);
        ticketString += "_____________________________________\n";
        thermalPrinter.printLine(buffer);
        thermalPrinter.printLine(buffer);
        thermalPrinter.setStep(12);
        thermalPrinter.setFont(ThermalPrinter.ASC8X16B,ThermalPrinter.HZK12);
        thermalPrinter.print("SIGNATURE\n");
        ticketString += "(CARDHOLDER SIGNATURE)\n";
        thermalPrinter.setStep(30);
        Arrays.fill(buffer, (byte) 0);
        for(i=0;i<40;i++)
            buffer[i]=(byte) 0xfc;
        ret = thermalPrinter.printLine(buffer);
        thermalPrinter.printLine(buffer);
        ticketString += "_____________________________________\n";
        thermalPrinter.setStep(4);
        thermalPrinter.print("I ACKNOWLEDGE SATISFACTORY RECEIPT\n");
        ticketString += "I ACKNOWLEDGE SATISFACTORY RECEIPT\n";
        thermalPrinter.print("  OF RELATIVE GOODS/SERVICE\n");
        ticketString += "  OF RELATIVE GOODS/SERVICE\n";

        thermalPrinter.setStep(200);
        */
        thermalPrinter.setStep(200);
        ret=thermalPrinter.printStart();
        state = thermalPrinter.waitForPrintFinish();
        return state;
    }

}
