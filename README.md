# SU CS Club Collaborative Poster

This is an experiment to work on a collaborative CS Club poster.

The goal is to have a large amount of students contribute a "Manipulator" to the project, and with enough submissions, a poster can be divided up into sections and each student will have their code affecting a small part of a larger CS poster, creating a mosaic somewhat like a digital collaborative quilt.

## How to get started

Running the PosterMain class will open a simple GUI to test every Manipulator. 

![image](https://user-images.githubusercontent.com/53229958/218334434-55e5594f-c946-46f8-ae6b-273463130594.png)

To make your own Manipulator, simply create a new class file in the src/manipulators directory which extends the abstract Manipulator (If you need to, take a look at the example files in that same directory). Once created, your Manipulator will be automatically detected and show up in the dropdown when you run PosterMain.

### What is a Manipulator?
Manipulators are classes designed to manipulate the pixels of a black and white image. Each Manipulator is passed an image, and should output another image that has been processed. Simple Manipulators will only need to modify the `getColorAtPoint()` method, which modifys based on x and y coordinates. If you'd like to do a more complex pattern, you may want to override the `transformImage()` method instead, which will let you process pixels or groups of pixels in different ways than the default. 

I highly encourage checking out the Manipulator class and the example classes to figure out how everything works.


### The Display Panel
![image](https://user-images.githubusercontent.com/53229958/218334450-17a744af-281c-47f9-beac-3116674e30c5.png)

Through the dropdown menu, you are able to select a Manipulator to view and test. The two images displayed in this window represent the input (the left image), and the image after being passed through the Manipulator (the right image).

The "Generate New Random" button will generate a new random seed and regenerate the image. If your manipulator uses randomness, this will allow you view different possible outcomes.

The "Preview Big Image" button will allow you to see what the larger poster might look like with multiple Manipulators selected.

### The Preview Panel
![image](https://user-images.githubusercontent.com/53229958/218334496-452d118b-04f7-4ab6-8594-43cee184fffc.png)

By changing the two numbers in the top right, you can change the number of rows and columns in the image, which as more Manipulators are added may be necessary to see all of them.

Pressing the "Save Image" button will export the current mosaic being displayed into the project directory in the "output" folder. These images are marked with the current date and time, so you can save multiple images and not worry about them overwriting each other.

The list of checkboxes will allow you to toggle on/off specific manipulators in the mosaic, in case you want to exclude some Manipulators or view only a specific set of Manipulators.

## Contributing a Manipulator
To add your Manipulator to the project, you should [fork](https://docs.github.com/en/get-started/quickstart/fork-a-repo) this repository, and then [create a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request), which can then be accepted and merged.

## Extra Info

If you need assistance with code or GitHub or have any questions about anything, ask them in the [CS Club Discord](https://discord.gg/629fkuKAC3) in the "#collaborative-poster" channel, and someone will be able to help you.

If you come across a bug, crash, or error in the program, or would like to suggest a feature in the program, make an issue at [this link](https://github.com/SU-CS-Club/CollaborativePoster/issues/new)!

I will be updating this README as more features are added.
