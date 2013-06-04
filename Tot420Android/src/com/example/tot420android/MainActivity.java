package com.example.tot420android;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
//import android.widget.ScrollView;

public class MainActivity extends Activity {

	TextView textlog;//Log for outputs
	 
    Button buttonConnect;//(dis)connect Button
    SeekBar seekBar1;//Seekbar to control the Servo
    SeekBar seekBar2;//Seekbar to control the Servo
    SeekBar seekBar3;//Seekbar to control the Servo
    TextView seekBarValue;//Textfield displaing the Value of the seekbar
 
    Boolean connected=false;//stores the connection status
 
    DataOutputStream dataOutputStream = null;//outputstream to send commands
    Socket socket = null;//the socket for the connection
    int kalli=0;
      
    
    // *******************
    // ** Called when the activity is first created.
    // *******************
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        //connect the view and the objects
        buttonConnect = (Button)findViewById(R.id.button1);
        textlog = (TextView)findViewById(R.id.textView3);
        seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar)findViewById(R.id.seekBar3);
        //seekBarValue = (TextView)findViewById(R.id.seekbarvalue);
 
        textlog.setText("Starting Client"+'\n');//log that the App launched
        changeConnectionStatus(false);//change connectionstatus to "disconnected"
        
        //((ScrollView) findViewById(R.id.scrollView1)).fullScroll(View.FOCUS_DOWN);
 
        //Eventlisteners
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        seekBar1.setOnSeekBarChangeListener(seekbarchangedListener1);
        seekBar2.setOnSeekBarChangeListener(seekbarchangedListener2);
        seekBar3.setOnSeekBarChangeListener(seekbarchangedListener3);
    }
    
    
    
    // *******************
    // ** SEEKBAR 1 EVENTLISTENER
    // *******************
    
    SeekBar.OnSeekBarChangeListener seekbarchangedListener1 = new SeekBar.OnSeekBarChangeListener(){
        //Methd is fired everytime the seekbar is changed
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
            //seekBarValue.setText(valueOfseekbar);//update the value in the textfield
            
            outputText("set1:"+valueOfseekbar);
            
             if(connected){//if the socket is connected
                try {
                     //send a string to the Netduino Server in the form of "set: -seekbarvalue- \n"
                     dataOutputStream.writeBytes("set:"+valueOfseekbar+'\n');
            
                     
                 }catch (UnknownHostException e) {//catch and
                     outputText(e.getMessage());//display errors
                 } catch (IOException e) {//catch and
                     outputText(e.getMessage());//display errors
                 }
             }
        }
 
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
 
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    
    
 // *******************
    // ** SEEKBAR 2 EVENTLISTENER
    // *******************
    
    SeekBar.OnSeekBarChangeListener seekbarchangedListener2 = new SeekBar.OnSeekBarChangeListener(){
        //Methd is fired everytime the seekbar is changed
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
            //seekBarValue.setText(valueOfseekbar);//update the value in the textfield
            
            outputText("set2:"+valueOfseekbar);
            
             if(connected){//if the socket is connected
                 try {
                     //send a string to the Netduino Server in the form of "set: -seekbarvalue- \n"
                     dataOutputStream.writeBytes("set:"+valueOfseekbar+'\n');
            
                 }catch (UnknownHostException e) {//catch and
                     outputText(e.getMessage());//display errors
                 } catch (IOException e) {//catch and
                     outputText(e.getMessage());//display errors
                 }
             }
        }
 
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
 
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    
    
 // *******************
    // ** SEEKBAR 3 EVENTLISTENER
    // *******************
    
    SeekBar.OnSeekBarChangeListener seekbarchangedListener3 = new SeekBar.OnSeekBarChangeListener(){
        //Methd is fired everytime the seekbar is changed
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
            //seekBarValue.setText(valueOfseekbar);//update the value in the textfield
            
            outputText("set3:"+valueOfseekbar);
 
            if(connected){//if the socket is connected
                 try {
                     //send a string to the Netduino Server in the form of "set: -seekbarvalue- \n"
                     dataOutputStream.writeBytes("set:"+valueOfseekbar+'\n');
            
            	
                 }catch (UnknownHostException e) {//catch and
                     outputText(e.getMessage());//display errors
                 } catch (IOException e) {//catch and
                     outputText(e.getMessage());//display errors
                 }
             }
        }
 
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
 
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    
    
    
    // *******************
    // ** CONNECTION BUTTON EVENTLISTENER
    // *******************
    
    Button.OnClickListener buttonConnectOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            if(!connected){//if not connected yet
                outputText("connecting to Server");
                 try {//try to create a socket and outputstream
                	 outputText("kallig");
                      socket = new Socket("192.168.156.157", 8000);//create a socket //was port 8888
                      outputText("skarpig");
                      dataOutputStream = new DataOutputStream(socket.getOutputStream());//and stream
                      
                      outputText("successfully connected");//output the connection status
                      changeConnectionStatus(true);//change the connection status
                 } catch (UnknownHostException e) {//catch and
                	 outputText("unknown host exc");
                      outputText(e.getMessage());//display errors
                      
                      changeConnectionStatus(false);
                 } catch (IOException e) {//catch and
                     outputText(e.getMessage());//display errors
                     outputText("IOdavid exc");
                     changeConnectionStatus(false);
                     
                 }
            }else{
                outputText("disconnecting from Server...");
                try {//try to close the socket
                      socket.close();
                      outputText("successfully disconnected");
                      changeConnectionStatus(false);//change the connection status
                 } catch (UnknownHostException e) {//catch and
                	 outputText("unknown host exc2");
                      outputText(e.getMessage());//display errors
                 } catch (IOException e) {//catch and
                	 outputText("IO exc");
                      outputText(e.getMessage());//display errors
                 }
            }
    }};
    
    
    
    // *******************
    // **  Method changes the connection status
    // *******************
    
    public void changeConnectionStatus(Boolean isConnected) {
        connected=isConnected;//change variable
        seekBar1.setEnabled(isConnected);//enable/disable seekbar1
        seekBar2.setEnabled(isConnected);//enable/disable seekbar2
        seekBar3.setEnabled(isConnected);//enable/disable seekbar3
        if(isConnected){//if connection established
            buttonConnect.setText("disconnect");//change Buttontext
        }else{
            buttonConnect.setText("connect");//change Buttontext
        }
    }
    
    
 
    // *******************
    // **  Method appends text to the textfield and adds a newline character
    // *******************
 
    public void outputText(String msg) {
        textlog.append(msg+"\n");
    }

}