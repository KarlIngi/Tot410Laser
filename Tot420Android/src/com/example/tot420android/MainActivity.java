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

public class MainActivity extends Activity {

	
		TextView textlog;//Log for outputs
		
		Button buttonConnect;//(dis)connect Button
		SeekBar seekBar;//Seekbar to get value to be sent to Netduino
		SeekBar seekBar2;//Seekbar2 to get value to be sent to Netduino
		SeekBar seekBar3;//Seekbar3 to get value to be sent to Netduino
		TextView seekBarValue;//Textfield displaing the Value of seekbar
		TextView seekBarValue2;//Textfield displaing the Value of seekbar2
		TextView seekBarValue3;//Textfield displaing the Value of seekbar3
		
		Boolean connected=false;//stores the connectionstatus
		
		DataOutputStream dataOutputStream = null;//outputstream to send commands
		Socket socket = null;//the socket for the connection
		
		
		
		//-----------------------------------------
		//
		//Called when the activity is first created. 
		//
		//------------------------------------------
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
		    
	        //connect the view and the objects
		    buttonConnect = (Button)findViewById(R.id.connect);
		    textlog = (TextView)findViewById(R.id.textlog);
		    seekBar = (SeekBar)findViewById(R.id.seekbar);
		    seekBar2 = (SeekBar)findViewById(R.id.seekBar1);
		    seekBar3 = (SeekBar)findViewById(R.id.seekBar2);
		    seekBarValue = (TextView)findViewById(R.id.seekbarvalue);
		    seekBarValue2 = (TextView)findViewById(R.id.textView2);
		    seekBarValue3 = (TextView)findViewById(R.id.textView4);
		    
		    textlog.setText("Starting Client");//log that the App launched
		    changeConnectionStatus(false);//change connectionstatus to "disconnected"
		    
		    //Eventlisteners
		    buttonConnect.setOnClickListener(buttonConnectOnClickListener);
		    seekBar.setOnSeekBarChangeListener(seekbarchangedListener);
		    seekBar2.setOnSeekBarChangeListener(seekbarchangedListener2);
		    seekBar3.setOnSeekBarChangeListener(seekbarchangedListener3);
	    }
	    
	    
	    
	    // -----------------------------------------
	    //
	    //SEEKBAR EVENTLISTENER 
	    //
	    //------------------------------------------
	    SeekBar.OnSeekBarChangeListener seekbarchangedListener = new SeekBar.OnSeekBarChangeListener(){
	    	//Methd is fired everytime the seekbar is changed
	    	@Override
	 	   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	    		String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
	    		seekBarValue.setText(valueOfseekbar);//update the value in the textfield
	    		
		 	    if(connected){//if the socket is connected
		 	    	try {
		 	    		//send a string to the Netduino Server in the form of "A -seekbarvalue- \n"
		 	    		dataOutputStream.writeBytes("A"+valueOfseekbar+'\n');
		 	    	}catch (UnknownHostException e) {//catch and
		 	    		outputText(e.getMessage());//display errors in console
		 			} catch (IOException e) {//catch and
		 				outputText(e.getMessage());//display errors in console
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
	    
	    
	    // --------------------------------------
	    //
	    //SEEKBAR 2 EVENTLISTENER
	    //
	    //--------------------------------------
	    SeekBar.OnSeekBarChangeListener seekbarchangedListener2 = new SeekBar.OnSeekBarChangeListener(){
	    	//Methd is fired everytime the seekbar is changed
	    	@Override
	 	   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	    		String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
	    		seekBarValue2.setText(valueOfseekbar);//update the value in the textfield
	    		
		 	    if(connected){//if the socket is connected
		 	    	try {
		 	    		//send a string to the Netduino Server in the form of "B -seekbarvalue- \n"
		 	    		dataOutputStream.writeBytes("B"+valueOfseekbar+'\n');
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
	    
	    
	    
	    // ----------------------------------------
	    //
	    //SEEKBAR 3 EVENTLISTENER
	    //
	    //------------------------------------------
	    SeekBar.OnSeekBarChangeListener seekbarchangedListener3 = new SeekBar.OnSeekBarChangeListener(){
	    	//Methd is fired everytime the seekbar is changed
	    	@Override
	 	   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	    		String valueOfseekbar = String.valueOf(progress);//save the value of the seekbar in a string
	    		seekBarValue3.setText(valueOfseekbar);//update the value in the textfield
	    		
		 	    if(connected){//if the socket is connected
		 	    	try {
		 	    		//send a string to the Netduino Server in the form of "C -seekbarvalue- \n"
		 	    		dataOutputStream.writeBytes("C"+valueOfseekbar+'\n');
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
	    
	    
	    // ------------------------------------------
	    //
	    //CONNECTION BUTTON EVENTLISTENER
	    //
	    //-------------------------------------------
	    Button.OnClickListener buttonConnectOnClickListener = new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(!connected){//if not connected yet
					outputText("connecting to Server");
					 try {//try to create a socket and outputstream
						  socket = new Socket("192.168.157.120", 8000);//create a socket
						  dataOutputStream = new DataOutputStream(socket.getOutputStream());//and stream
						  outputText("successfully connected");//output the connection status
						  changeConnectionStatus(true);//change the connection status
					 } catch (UnknownHostException e) {//catch and
						  outputText(e.getMessage());//display errors
						  changeConnectionStatus(false);
					 } catch (IOException e) {//catch and
						 outputText(e.getMessage());//display errors
						 changeConnectionStatus(false);
					 }
				}else{
					outputText("disconnecting from Server...");
					try {//try to close the socket
						  socket.close();
						  outputText("successfully disconnected");
						  changeConnectionStatus(false);//change the connection status
					 } catch (UnknownHostException e) {//catch and
						  outputText(e.getMessage());//display errors
					 } catch (IOException e) {//catch and
						  outputText(e.getMessage());//display errors
					 }
				}
		}};
		
		
		
		//-----------------------------------------
		//
		// Method changes the connection status
		//
		//-----------------------------------------
		public void changeConnectionStatus(Boolean isConnected) {
			connected=isConnected;//change variable
			seekBar.setEnabled(isConnected);//enable/disable seekbar
			seekBar2.setEnabled(isConnected);//enable/disable seekbar 2
			seekBar3.setEnabled(isConnected);//enable/disable seekbar 3
			if(isConnected){//if connection established
				buttonConnect.setText("disconnect");//change Buttontext
			}else{
				buttonConnect.setText("connect");//change Buttontext
			}
		}
		
		
		//-----------------------------------------------------------------
		//
		//Method appends text to the textfield and adds a newline character
		//
		//-------------------------------------------------------------------
		public void outputText(String msg) {
			textlog.append(msg+"\n");
		}
		
		
		
		//---------------------------------------------
		//
		//Called when the activity is destroied. 
		//
		//-----------------------------------------------
		@Override
	    public void onDestroy() {
			if(connected)
				try {//try to close the socket
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
	    }
	}