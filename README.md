
# AR Landmark App
By: Jack Prevatt

AR Landmark App is a simple Android application that uses ARCore and Sceneform to let users explore famous landmarks in augmented reality. Users can select a landmark, view detailed information about it, and place a 3D model of the landmark on a surface in their surroundings.

# Features

- Interactive Landmark Selection: Choose from a variety of landmarks, such as the Statue of Liberty, Christ the Redeemer, and the Eiffel Tower.
- Augmented Reality Integration: Place and view 3D models in AR using ARCore.
- Landmark Details: Displays information about the selected landmark.
- Surface Detection: Tap on detected AR surfaces to place the landmark model.
- Toggle Information: Show or hide landmark details while viewing the AR scene.
- Back Navigation: Easily return to the landmark selection screen.

# Technologies used

- Android Studio
- Java
- ARCore
- Sceneform
- GLB 3D models

# Setup Instructions
Prerequisites

1. Android Studio latest version (gradle version 8.9)
2. Android device that supports ARCore
3. ARCore installed on device

# How to Use

1. Launch the app.
2. Select a landmark from the list.
3. Grant camera permissions if prompted.
4. Grant camera permissions if prompted.
5. Aim your device at a flat surface to detect it (ARCore will highlight the surface).
6. Tap on the detected surface to place the 3D model.
7. Use the Toggle Info button to show/hide landmark details.
8. Use the Back button to return to the main menu.

# Known Issues

1. "No RCB file at URI" Error:
   - Certain versions of Sceneform are known to have issues rendering 3D .glb files.
3. ARCore Not Supported:
   - Ensure the device is ARCore compatible and has the latest ARCore version installed.

# Future Enhancements

1. Fix model loading issues (Sceneform)
2. Add more landmarks
3. Allow scaling and rotation of 3D models in AR.
