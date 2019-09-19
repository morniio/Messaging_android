
# Android Morni Messaging Center
### A Sample Library to describe new way of viewing a list of standard messages and its details for Morni users over all Morni Android Apps.  

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Programming Practices Followed](#programming-practices-followed)
* [Pre-requisites](#pre-requisites)
* [Build requirements](#build-requirements)
* [Setup](#setup)
-- [Current Version](#current-version)
-- [Use Gradle](#use-gradle)
-- [Or Maven](#or-maven)
* [Usage](#usage)
-- [For Kotlin](#for-kotlin)
-- [For Java](#for-java)
* [Customization and advanced options](#customization-and-advanced-options)
* [Features](#features)
* [Status](#status)
* [License](#license)

## General info
This library is written in Kotlin, you can use it  in your Android App to get list of messages and able display its content.

## Screenshots
![messages_list](https://user-images.githubusercontent.com/55134740/65234606-2d098a80-dad5-11e9-8580-d93ceef3c433.png) ![message_details](https://user-images.githubusercontent.com/55134740/65234605-2d098a80-dad5-11e9-9698-1cdc22381ecb.png) 

## Programming Practices Followed
-	Kotlin Programming Language
-	MVVM
-	AndroidX Support Library
-  AndroidX Architecture Components(ViewModels, LiveData, Navigation, Paging)
-  AndroidX Data Binding
-  RxJava2
-  Koin for Dependency Injection
-  Retrofit2 with Okhttp

## Pre-requisites

- Android Studio 3.5 or higher
- Enable Data binding
- Enable multiDex


    dataBinding.enabled = true
    multiDexEnabled true

## Build requirements

-   minSdkVersion is API 19 (KitKat 4.4) or higher.
-   Android SDK platform: API 29 (Q, 10.0) or lower.

## Setup

### Current Version

    // latest stable
    morni_lib_version= '0.1.0'

### Use Gradle

Step 1: Add the token to $HOME/.gradle/gradle.properties

    authToken=jp_2pk2dc4rt2ptihlnafamrp6oj4

Step 2: Add it in your root build.gradle at the end of repositories:

    allprojects {
    	repositories {
    		...
    		maven {
	            url "https://jitpack.io"
	            credentials { username authToken }
	        }
        }
     }

  Step 3: Add the dependency

    dependencies {
    	...
    	implementation "com.github.morniio:Messaging_android:$morni_lib_version"
    }

### Or Maven

Strep 1: Add the token to $HOME/.m2/settings.xml as the username

    <settings>
      <servers>
            <server>
              <id>jitpack.io</id>
              <username>jp_2pk2dc4rt2ptihlnafamrp6oj4</username>
              <password>.</password>
            </server>
      </servers>
    </settings>

Step 2: Add dependencies

    <repositories>
    	<repository>
    		<id>jitpack.io</id>
    		<url>https://jitpack.io</url>
    	</repository>
    </repositories>
        
    <dependency>
    	<groupId>com.github.morniio</groupId>
    	<artifactId>Messaging_android</artifactId>
        <version>morni_lib_version</version>
    </dependency>

## Usage

Use "IntentIntegration" class for lunching the library with customize options 

### For Kotlin

    val integrator = IntentIntegrator(this) // passing current activity
    integrator.setBaseUrl("end-point path") // mandatory
    integrator.setAccessToken("user access token") // mandatory
    integrator.setLanguage("device language") // optional and the default language is "ar"
    integrator.setAppVersion("end-point version") // mandatory
    integrator.setPageSize(10) // optional and the default value is 10
    integrator.initiate()


    // Get the results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)  
        if (result != null) {  
            if (result.status == 401) {  
                // Un Authorized Login
                Toast.makeText(this, "Un Authorized Login", Toast.LENGTH_LONG).show()   
     }  
        } else {  
            super.onActivityResult(requestCode, resultCode, data)  
        }  
    }

### For Java

    IntentIntegrator integrator = new IntentIntegrator(this); // passing current activity  
    integrator.setBaseUrl("end-point path"); // mandatory  
    integrator.setAccessToken("user access token");  // mandatory  
    integrator.setLanguage("device language"); // optional and the default language is "ar"  
    integrator.setAppVersion("end-point version");  // mandatory  
    integrator.setPageSize(10);// optional and the default value is 10  
    integrator.initiate();
    
    
	// Get the results
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  
        IntentResult result = IntentIntegrator.Companion.parseActivityResult(requestCode, resultCode, data);  
        if (result != null) {  
            if (result.getStatus() == 401) {  
                Toast.makeText(this, "Un Authorized Login", Toast.LENGTH_LONG).show();  
            }  
        } else {  
            super.onActivityResult(requestCode, resultCode, data);  
        }  
    }
## Customization and advanced options

#### To customize single message design, as bellow figure, follow the next steps:
- Create new .xml file in your project with name "default_morni_message_row_layout"
- Make your new .xml include these ids:
-- container_view: for the root layout in my case its CardView.
-- tv_title: For the TextView that hold the Message title.
-- tv_date: For the TextView that hold Message creation date.
-- tv_body: For the TextView that hold Message body.
-- img_new: For the ImageView that hold Message status (read/un-read).

![enter image description here](https://user-images.githubusercontent.com/55134740/65246789-82529580-daef-11e9-8ca4-b64b34d79c41.png)



## Features
List of features ready
-   List all read and unread messages received from end-point
-   Display the full content of each message

## Status
Project is: under testing

## License

MorniMessaging is available under the MIT license. See the LICENSE file for more info.
