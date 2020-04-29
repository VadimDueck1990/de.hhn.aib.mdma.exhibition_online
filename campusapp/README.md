# Framework7 Cordova Project

Tabs (multiple views) Framework7 starter app template.

## Usage

### 1. Download this repository
```
git clone https://git.it.hs-heilbronn.de/it/courses/mim/vs/vs-sose-19/room-changers/roomchangers.git
```


### 2. Instal dependencies

Go to the downloaded repository, open the campusapp folder in a Terminal and run:
```
npm install
```

This will download the required node_modules, then:

```
cordova prepare
```

to restore cordova platforms and plugins.

### 3. Build
cordova platforms android and browser are added.

make sure your environment is set up with all SDKs to build!
Instructions on how to setup your environment for specific Platforms can be found in the [Cordova Documentation](https://cordova.apache.org/docs/en/latest/) for this Platform. 

!!! Note: cordova build will throw an error if your environment is not set up for Android. Try cordova build browser instead.
```
cordova build
```

will build the application for all platforms

you can also specify a single platform to build, for example:

```
cordova build browser
```

further platforms can be added:

```
cordova platform add ios
```

### 4. Run the app

```
cordova run browser
```
Is the most common way to test your changes.
App will be opened in browser at `http://localhost:8000/`

if you installed Android Studio and set up the SDK you can run the app on a plugged in android device with

```
cordova run android --device
```

USB debugging has to be enabled!
