
# React Native Android Inventory

## Table of contents

* [Synopsis](#synopsis)
* [Installation](#installation)
* [Usage](#usage)
* [Documentation](#documentation)
* [Contribute](#contribute)
* [Professional Services](#professional-services)

## Synopsis

The React Native Inventory Library collects a complete inventory of your Android devices. It allows you to export your inventory in a beautiful XML or JSON as protocol compatible with FusionInventory for GLPI.

You can find more information about the Inventory Protocol here:
<http://fusioninventory.org/documentation/dev/spec/protocol/inventory.html>

### Data collected

- Account Info
- Accesslog
- Hardware
- User
- Storages
- Operating System
- BIOS
- Memories
- Inputs
- Sensors
- Drives
- CPUs
- Videos
- Cameras
- Networks
- Environments variables
- JVMS
- Softwares
- USB
- Battery
- Controllers

Visit our [website](http://flyve.org/android-inventory-library/) for every element specification.

## Instalation

`$ npm install react-native-android-inventory --save`

### Mostly automatic installation

`$ react-native link react-native-android-inventory`

### Manual installation

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
      implementation project(':react-native-android-inventory')
  	```

## Usage
```javascript
import CreateInventory from 'react-native-android-inventory';

// Instead of appVersion, put the number.
    CreateInventory.createInventory("appVersion");
```
## Documentation

We maintain a detailed documentation of the project on the website, check the [How-tos](http://flyve.org/android-inventory-library/howtos/) and [Development](http://flyve.org/android-inventory-library/) section.

## Contribute

Want to file a bug, contribute some code, or improve documentation? Excellent! Read up on our
guidelines for [contributing](./CONTRIBUTING.md) and then check out one of our issues in the [Issues Dashboard](https://github.com/CamposErik/React-Native-Android-Inventory/issues).

## Professional Services

The Flyve MDM and GLPI Network services are available through our [Partner's Network](http://www.teclib-edition.com/en/partners/). We provide special training, bug fixes with editor subscription, contributions for new features, and more.

Obtain a personalized service experience, associated with benefits and opportunities.
