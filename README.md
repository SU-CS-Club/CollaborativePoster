# SU CS Club Collaborative Poster

This is an experiment to work on a collaborative CS Club poster.

The poster will be divided up into sections, and each person can create a "Manipulator" to modify their section of the image, collectively coming together as a poster.

## How to Use

Running the PosterMain class will open a simple GUI to test every Manipulator. To add your own, simply add a class which extends the abstract Manipulator, and place your file in the src/manipulators directory. It will be automatically detected and show up in the dropdown when you run PosterMain.

The dropdown specifies what Manipulator to use, and the effect is shown on the two images:
The left image is the input, and the right image is the processed version.

The "Generate New Random" button will generate a new random seed and regenerate the image. If your manipulator uses randomness, this will allow you view different possible outcomes.

## Extra Info

I will update this README as more features are added.
