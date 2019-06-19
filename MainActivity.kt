package com.example.lesson8
//ToDo practice linking to other activies and other pages
//To use the app on the activity page, click on the Lesson 8 menu symbol top right in menu bar, 3 options will be presented, all are links to different web pages
// delete "Search" in the text box and enter a valid web site eg. www.facebook.com
//the notification bar will appear asking you to confirm the web site address is correct, click yes, and then the page will be displayed in a WebView
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //class attributes for the date to be used in notification box
    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //webView
        webView1.setWebViewClient(WebViewClient())
        webView1.settings.javaScriptEnabled = true
        webView1.settings.loadsImagesAutomatically = true
        //load the webView from the menu bar and user input
        webView1.loadUrl(" ")

        //create the Notification box - that opens when a user clicks outlook url from menu bar

        // Define a channel for notificaitons to appear on top of our app from android studio lib
        val myNotificaitonManager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
        val id = "Outlook login date"
        val name = "$formatter"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)
        channel.lightColor = Color.CYAN
        myNotificaitonManager.createNotificationChannel(channel)


        //submit button
        btnSubmit.setOnClickListener(
            View.OnClickListener {
                //Now add a snackBar btnSnackbar
                var builder = Snackbar.make(
                    it,
                    "Is your web address correct?", Snackbar.LENGTH_LONG
                )
                //add a confirm prompt to the snackbar
                builder.setAction("Confirm",View.OnClickListener {
                    //change the webView if user does confirm
                    submit()
                })

                builder.show()


            }
        )

    }
    //submit method for clicking on submit button
    fun submit(){
        //get the user input text and make it toString
        var temp = txtInput.text
        var str = temp.toString()
        //reset the webView to the users input
        webView1.setWebViewClient(WebViewClient())
        webView1.loadUrl("https://$str")
        txtInput.text.clear()
    }
    //menu creation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true;
    }
    //inherit an existing object - A class from AppDefaultActivity, so we override the behavior to show what we want to show instead of default
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item1 -> {
                txtInput.text.clear()
                webView1.setWebViewClient(WebViewClient())
                webView1.loadUrl("http://www.androidatc.com")
                return super.onOptionsItemSelected(item)
                //return the item selected, the item we pass through
            }
            R.id.item2 -> {
                txtInput.text.clear()
                webView1.setWebViewClient(WebViewClient())
                webView1.loadUrl("https://moodle.itpt.co.uk")

                return super.onOptionsItemSelected(item)
            }
            R.id.item3 -> {
                txtInput.text.clear()
                webView1.setWebViewClient(WebViewClient())
                webView1.loadUrl("https://outlook.office.com/mail")
                //add a notification (import & create channel ) with this date and time for logging into outlook
                //later this can be added to a log
                notificaiton()
               // see btnNotificaiton code
                return super.onOptionsItemSelected(item)


            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
        @RequiresApi(Build.VERSION_CODES.O)
        fun notificaiton(){

                var nCBuilder = NotificationCompat.Builder(this, "Channel_01")
                nCBuilder.setSmallIcon(R.drawable.notification_icon_background)
                nCBuilder.setContentTitle("NOTIFICATION")
                nCBuilder.setContentText("Outlook Login date $formatter")
                //Android app allows us to use a precreated pop up over our app NotificationService
                var mNotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE)
                            //references as a notificaiton Manager
                            as NotificationManager

                mNotificationManager.notify(1, nCBuilder.build())
                //channel O1 does not exit yet so needs to be coded




        }

}
