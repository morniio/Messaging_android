[![](https://jitpack.io/v/morniio/Messaging_android.svg)](https://jitpack.io/#morniio/Messaging_android)
# Android Morni Messaging Center
### A Sample Library to describe new way of viewing a list of standard messages and its details for Morni users over all Morni Android Apps.  

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Programming Practices Followed](#programming-practices-followed)
* [Pre-requisites](#pre-requisites)
* [Build requirements](#build-requirements)
* [Setup](#setup)
	- [Current Version](#current-version)
	- [Use Gradle](#use-gradle)
	- [Or Maven](#or-maven)
* [Usage](#usage)
	- [For Kotlin](#for-kotlin)
	- [For Java](#for-java)
* [Customization and advanced options](#customization-and-advanced-options)
	* [Customize Message Cell](#customize-message-cell)
	* [Customize Message Details screen](#customize-message-details-screen)
* [Features](#features)
* [Status](#status)
* [License](#license)

## General info
This library is written in Kotlin, you can use it  in your Android App to get list of messages and able display its content.

## Screenshots
![messages_list](https://user-images.githubusercontent.com/55134740/65234606-2d098a80-dad5-11e9-8580-d93ceef3c433.png) ![message_details](https://user-images.githubusercontent.com/55134740/65234605-2d098a80-dad5-11e9-9698-1cdc22381ecb.png) 

## Programming Practices Followed
- Kotlin Programming Language.
- MVVM.
- AndroidX Support Library.
- AndroidX Architecture Components(ViewModels, LiveData, Navigation, Paging).
- RxJava2.
- Koin for Dependency Injection.
- Retrofit2 with Okhttp.

## Pre-requisites

- Android Studio 3.5 or higher
- Enable multiDex

    multiDexEnabled true

## Build requirements

-   minSdkVersion is API 19 (KitKat 4.4) or higher.
-   Android SDK platform: API 29 (Q, 10.0) or lower.

## Setup

### Current Version

    // Put here the latest version
    morni_lib_version= '1.0.7'

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

Step 1: Add the token to $HOME/.m2/settings.xml as the username

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
    
### Proguard Configuration

Strep 1: Add the following lines to the root gradle file (build.gradle: Project: ...), to be able to generate signed apk and stop proguard issues.

	buildscript {
   	    ...
	    configurations.all {
		resolutionStrategy {
		    force 'net.sf.proguard:proguard-gradle:6.2.0' // Put the latest ProGuard Gradle version.
		}
	    }
	}

Step 2: Add the following line to proguard rules file:

	-keepclassmembers enum * { *; }


## Usage

Use "MorniMessagesSdk" class for lunching the library with customize options 

     MorniMessagesSdk(it).apply {
            setHttpHeader("auth-interceptor-of-the-current-app") // mandatory
            setBaseUrl("server-url") // mandatory
            setAccessToken("user-access-token") // mandatory
            setLanguage("device-language") // optional and the default language is "ar"
            initiate()
        }
	
    // Get the results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
        val result = MorniMessagesSdk.parseActivityResult(requestCode, resultCode, data)  
        if (result != null) super.onActivityResult(requestCode, resultCode, data)  
    }


## Customization and advanced options

### Customize Message Cell
#### To customize single message design, as bellow figure, follow the next steps:
- Create new .xml file in your project with name "default_morni_message_row_layout"
- Make your new .xml include these ids:
	- container_view: for the root layout in my case its CardView.
	- tv_title: For the TextView that hold the Message title.
	- tv_date: For the TextView that hold Message creation date.
	- tv_body: For the TextView that hold Message body.
	- img_new: For the ImageView that hold Message status (read/un-read).

![enter image description here](https://user-images.githubusercontent.com/55134740/65246789-82529580-daef-11e9-8ca4-b64b34d79c41.png)

#### To customize footer cell, as bellow figure, follow next steps:
- Create new .xml file in your project with name "default_footer_layout"
- Make your new .xml include these ids:
	- progress_bar: For the ProgressBar.
	- txt_error: For the TextView that hold error message.
	
	![loading](https://user-images.githubusercontent.com/55134740/65386194-8c1b0980-dd38-11e9-849c-28d65c167c9b.png)![no internet](https://user-images.githubusercontent.com/55134740/65386195-8de4cd00-dd38-11e9-8601-c762031d5065.png)

### Customize Message Details screen
#### To customize message details design, as bellow figure, follow the next steps:
- Create new .xml file in your project with name "default_morni_message_details_fragment"
- Make your new .xml include these ids:
	- toolbar: For the Toolbar
		- or use our custom toolbar, by including it in your xml [See](#include-library-toolbar).
	- progress_bar: For the ProgressBar.
	- txt_error: For the TextView that hold error message.
	- btn_retry: For Button to retry again
	- cv_details: For the details container in my case its CardView.
	- tv_title: For the TextView that hold the Message title.
	- tv_date: For the TextView that hold Message creation date.
	- tv_body: For the TextView that hold Message body.


![message_details](https://user-images.githubusercontent.com/55134740/65386232-18c5c780-dd39-11e9-9df5-371b52c69456.png)

#### Include Library Toolbar

    <include  
      android:id="@+id/toolbar_container"  
      layout="@layout/toolbar_container"  
      app:layout_constraintEnd_toEndOf="parent"  
      app:layout_constraintStart_toStartOf="parent"  
      app:layout_constraintTop_toTopOf="parent" />

  
## Features
List of features ready
-   List all read and unread messages received from end-point
-   Display the full content of each message

## Status
Project is: released to production.

## License

MorniMessaging is available under the MIT license. See the LICENSE file for more info.




