# Invoice

A native Android Java prototype for an Arabic invoice-entry workflow. It contains sign-in/registration screens, a main menu, and a form with a date picker and automatic quantity × unit-price calculation.

## Implemented behavior

- Sign-in and registration forms validate fields and navigate between activities.
- The main menu opens the invoice-entry activity.
- Invoice entry accepts invoice number, customer name, date, item description, quantity, and unit price.
- A DatePickerDialog sets the date, and TextWatcher updates the calculated total.
- Save validates required text fields, shows a success toast, and closes the form.

**Prototype limits:** sign-in does not check stored accounts. Save does not persist an invoice. The all-invoices button is a placeholder toast. There is no database, server API, PDF export, payment, or working invoice-history list.

## Stack and structure

Java, Android XML layouts, AndroidX AppCompat, Material Components, and View Binding. compileSdk/targetSdk are 36 and minSdk is 24. The existing project uses Android Gradle Plugin 8.2.2 and a Gradle 9.0 milestone wrapper; this review retained them.

```text
app/src/main/java/com/example/invoice/
  LoginActivity.java          Launcher; field checks and navigation
  RegisterActivity.java       Registration form
  MainActivity.java           Menu and placeholder history action
  CreateInvoiceActivity.java  Form, date picker, and total calculation
app/src/main/res/layout/      Activity layouts
gradle/wrapper/               Existing Gradle wrapper
```

## Build and run

Install an Android SDK with platform 36 and a JDK compatible with the existing Gradle/AGP combination. Set ANDROID_HOME or configure your SDK through Android Studio. The debug build was verified locally using JDK 17:

```powershell
.\gradlew.bat assembleDebug --console=plain
# With an authorized emulator/device connected:
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example.invoice/.LoginActivity
```

Open the repository in Android Studio to choose a device and run interactively. Do not use real credentials in these demonstration-only account forms.

## Screenshots and status

The debug APK built successfully. AGP reports that compileSdk 36 is newer than its tested range, and SDK/Gradle compatibility warnings remain. No configured AVD or connected device was available, so runtime and emulator screenshots were not validated. Capture the actual login, menu, and invoice form on an emulator/device as the next step; no screenshots were fabricated.
