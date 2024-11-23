
  
# Shimmer Animation
- ![kotlin](https://img.shields.io/badge/Platforms-Kotlin_Compatible-lightblue?style=for-the-badge&logo=kotlin)
- ![kotlin](https://img.shields.io/badge/Made_With-Kotlin-0095D9?style=for-the-badge&logo=kotlin)
- ![android_7](https://img.shields.io/badge/Android-7.0_Nougat-green?style=for-the-badge)
- ![min_sdk](https://img.shields.io/badge/minSdk-24-orange?style=for-the-badge)

<p align="center">A Shimmer Animation library for Kotlin.</p>
<p align="center">
<img width=30% src="./examples/example shimmer animation base.gif">
<img width=27% src="./examples/example shimmer animation.gif">
</p>


## 📦 Features

- 🌈 **Customizable Direction**  
  Tailor the shimmer animation’s direction to fit your design needs:  
  - **LEFT_TO_RIGHT**: Shimmer moves from left to right  
  - **TOP_TO_BOTTOM**: Shimmer moves from top to bottom  
  - **RIGHT_TO_LEFT**: Shimmer moves from right to left  
  - **BOTTOM_TO_TOP**: Shimmer moves from bottom to top

- 🛠 **Customizable Shape**  
  Choose the shimmer effect that best suits your UI:  
  - **LINEAR**: A simple, linear shimmer effect  
  - **RADIAL**: A circular, radial shimmer effect  

- 🔥 **Customizable Intensity**  
  Adjust the shimmer intensity to create the desired visual impact that matches your app’s style.

- ✨ **Customizable Fadeout**  
  Smoothly control the fadeout effect of the shimmer animation for seamless transitions.

- 🔁 **Customizable Repeat Count**  
  Define how many times the shimmer animation will repeat, giving you full control over the effect duration.

- ⏳ **Customizable Animation Duration**  
  Set the duration of each shimmer cycle, allowing you to control how long the effect lasts.

- 🕒 **Customizable Repeat Delay**  
  Manage the time interval between consecutive shimmer cycles for a more natural animation flow.

- 🚀 **Customizable Start Delay**  
  Set a delay before the shimmer animation begins to create a more dynamic timing sequence.

## 🛠 Installation
**Step 1.**  Add it in your root setting.gradle at the end of repositories: 
```css
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()

        		// Include JitPack, a repository that allows you to fetch dependencies from GitHub projects.
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2.**  Add the dependency

```css
	// This dependencies block is for the build.gradle
	dependencies {
	        implementation 'com.github.zaminalirustamov:Shimmer_Animation:1.0.2'
	}
```
## 🚀 Usage
### Example

```xml
<!-- Shimmer Animation for Toolbar -->  
<az.lahza.shimmer_animation.shimmer.ShimmerFrameLayout  
	  android:id="@+id/shimmer_animation_toolbar"  
	  android:layout_width="wrap_content"  
	  android:layout_height="wrap_content"  
	  android:layout_marginVertical="16dp"  
	  app:layout_constraintEnd_toEndOf="parent"  
	  app:layout_constraintStart_toStartOf="parent"  
	  app:layout_constraintTop_toTopOf="parent">  
  
		  <androidx.constraintlayout.widget.ConstraintLayout  
			  android:layout_width="wrap_content"  
			  android:layout_height="wrap_content">  
  
			  <include  
				  layout="@layout/layout_shimmer_placeholder"  
				  android:layout_width="100dp"  
				  android:layout_height="25dp" />  
	  </androidx.constraintlayout.widget.ConstraintLayout>  
  
</az.lahza.shimmer_animation.shimmer.ShimmerFrameLayout>
```

```xml
<!-- Shimmer Animation for Data -->  
<az.lahza.shimmer_animation.shimmer.ShimmerFrameLayout  
	  android:id="@+id/shimmer_animation_data"  
	  android:layout_width="match_parent"  
	  android:layout_height="0dp"  
	  app:layout_constraintBottom_toBottomOf="parent"  
	  app:layout_constraintEnd_toEndOf="parent"  
	  app:layout_constraintStart_toStartOf="parent"  
	  app:layout_constraintTop_toBottomOf="@id/shimmer_animation_section_name">  
  
	  <androidx.constraintlayout.widget.ConstraintLayout  
		  android:layout_width="match_parent"  
		  android:layout_height="match_parent">  
  
		  <include  
			  android:id="@+id/first_item"  
			  layout="@layout/layout_item_data"  
			  android:layout_width="wrap_content"  
			  android:layout_height="wrap_content" />  
  
		 <include  
			 android:id="@+id/second_item"  
			 layout="@layout/layout_item_data"  
			 android:layout_width="wrap_content"  
			 android:layout_height="wrap_content"  
			 app:layout_constraintTop_toBottomOf="@id/first_item" />  
			   
		 <include  
			 android:id="@+id/third_item"  
			 layout="@layout/layout_item_data"  
			 android:layout_width="wrap_content"  
			 android:layout_height="wrap_content"  
			 app:layout_constraintTop_toBottomOf="@id/second_item" />  
			   
		 <include  
			 android:id="@+id/fourth_item"  
			 layout="@layout/layout_item_data"  
			 android:layout_width="wrap_content"  
			 android:layout_height="wrap_content"  
			 app:layout_constraintTop_toBottomOf="@id/third_item" />  
  
	  </androidx.constraintlayout.widget.ConstraintLayout>  
  
</az.lahza.shimmer_animation.shimmer.ShimmerFrameLayout>
```

For more examples, check out the code

## 🤝 Contribution
I highly appreciate and welcome any issue reports, feature requests, pull requests, or GitHub stars you may provide.

<a href="https://www.buymeacoffee.com/zaminalirustamov" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>
