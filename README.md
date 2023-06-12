# image-viewer-jetpack

### Step 1. Add the JitPack repository to your build file

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	
### Step 2. Add the dependency

dependencies {
	        implementation 'com.github.outcode-aashutosh:image-viewer-jetpack:1.0.0'
	}


### Usage

Import ImageViewer class anywhere in your project.

ImageViewer(imageUrl: "Insert Image Url")

Defaults ContentScale is set to Fit. You can customize as per your need.


### Features

- FullScreen Image Viewer Component
- Pinch in/out to zoom the image


