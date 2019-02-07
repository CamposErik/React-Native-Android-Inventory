
# react-native-android-inventory

## Getting started

`$ npm install react-native-android-inventory --save`

### Mostly automatic installation

`$ react-native link react-native-android-inventory`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-android-inventory` and add `RNAndroidInventory.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAndroidInventory.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAndroidInventoryPackage;` to the imports at the top of the file
  - Add `new RNAndroidInventoryPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-inventory'
  	project(':react-native-android-inventory').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-inventory/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-inventory')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAndroidInventory.sln` in `node_modules/react-native-android-inventory/windows/RNAndroidInventory.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Android.Inventory.RNAndroidInventory;` to the usings at the top of the file
  - Add `new RNAndroidInventoryPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAndroidInventory from 'react-native-android-inventory';

// TODO: What to do with the module?
RNAndroidInventory;
```
  