# SQDatabase
This is the library that provide simlifying of the working with **Native Android Database**

## How to start
* [Download](https://sites.google.com/site/androidtutorialmugetsu/android/sqlib-android-sqlite-cover-library/sqlib.aar?attredirects=0&d=1) **aar** file;
* Put into **libs** folder inside your Android project;
* Add this code to your gradle file:
```gradle
apply plugin: 'com.android.application'

repositories {
    flatDir {
        dirs 'libs'
    }
}

android {
    //...
    defaultConfig {
        //...
    }
    buildTypes {
        release {
            //...
        }
    }
}

dependencies {
    //...
    compile(name: 'sqlib', ext: 'aar') {
        transitive = true;
    }
    compile 'com.android.support:support-annotations:23.0.0'
    //...
}
```
* Redownload dependencies and do ```Clean/Build```
