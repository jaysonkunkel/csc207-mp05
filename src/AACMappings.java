import structures.AssociativeArray;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/**
 * Keeps track of the complete set of AAC mappings.
 * 
 * @author Jayson Kunkel
 */
public class AACMappings{

  // holds the names of categories on the home screen and their subcategories
  AACCategory topLevelCategory;

  // holds the name and subcategories of the currently selected category
  AACCategory currentCategory; 

  // mappings of categories and their image locations
  AssociativeArray<String, AACCategory> categories;

  /**
   * Reads in the file, creates mappings from images to categories, add all items to each category.
   * Starts the AAC on the home screen.
   * 
   * @param filename the file to read in
   */
  public AACMappings(String filename){
    categories = new AssociativeArray<String, AACCategory>();

    // sets toplevelCategory and currentcategory to the home screen
    this.topLevelCategory = new AACCategory("");
    this.currentCategory = topLevelCategory;

    try {
      // read from given filename
			Scanner scanner = new Scanner(new File(filename));

      // while there is text in the file
			while (scanner.hasNextLine()) {
        // get and store the image location and text from a line
        String[] strs = scanner.nextLine().split(" ", 2);
        String imageLoc = strs[0];
        String text = strs[1];

        // if the line does not start with >, it is a top-level category
        // if it starts with >, it is a subcategory
        if(!imageLoc.substring(0, 1).equals(">")){
          // add item to top level
          topLevelCategory.addItem(imageLoc, text);
          // create a new category
          categories.set(imageLoc, new AACCategory(text));
          // set current category to it 
          try {
           currentCategory = categories.get(imageLoc); 
           //System.err.println("current: " + currentCategory.getCategory());
          } catch (Exception e) {
            System.err.println("Error: unable to get category");
          }
        } // if
        else{
          // add item to current category
          currentCategory.addItem(imageLoc.substring(1), text);
        } // else
			} // while

      // reset current category to the home screen
      currentCategory = topLevelCategory;

			scanner.close();

		} catch (Exception e) {
			System.err.println("Error: unable to read from file " + filename);
		}
    
  } // AACMappings(filename)

  /**
   * Adds the mapping to the current category (or the default category if that is the current category)
   * 
   * @param imageLoc the location of the image
   * @param text the text associated with the image
   */
  public void add(String imageLoc, String text){

    // if current category is the default category
    if(this.getCurrentCategory().equals("")){
      // add category to top level categories
      this.topLevelCategory.addItem(imageLoc, text);
      // store category and image location
      this.categories.set(imageLoc, new AACCategory(text));
    } // if
    else{
      // add subcategory to the current category
      this.currentCategory.addItem(imageLoc, text);
    }// else
  } // add(String, String)

  /**
   * Gets the current category
   * 
   * @return returns the current category or the empty string if on the default category
   */
  public String getCurrentCategory(){
    return this.currentCategory.name;
  } // getCurrentCategory()

  /**
   * Provides an array of all the images in the current category
   * 
   * @return the array of images in the current category
   */
  public String[] getImageLocs(){
    try {
      // get the image locations of the current category
      return this.currentCategory.getImages();
    } catch (Exception e) {
      System.err.println("Error: unable to get images");
       return new String[] {};
    }
  } // getImageLocs()

  /**
   * Given the image location selected, it determines the associated text with the image. 
   * If the image provided is a category, it also updates the AAC's current category to be 
   * the category associated with that image
   * 
   * @param imageLoc the location where the image is stored
   * @return returns the text associated with the current image
   */
  public String getText(String imageLoc){
    // if the current category is the default category
    if(this.getCurrentCategory().equals("")){
      try {
        //System.err.println(imageLoc);
        // set the current category to the category associated with given image
        currentCategory = categories.get(imageLoc);
        // return text associated with that category
        return topLevelCategory.getText(imageLoc);
      } catch (Exception e){
        System.err.println("Unable to grab category");
        return "";
      }
    } // if
    // else we are already in a category
    else{
      try {
        //System.err.println(imageLoc);
        // return the text associated with the given image
        return currentCategory.getText(imageLoc);
      } catch (Exception e) {
        System.err.println("Unable to grab image text");
        return "";
      }
    } // else
  } // getText(String)


  /* Sam said that the underlying code doesn't use isCategory */
  /**
   * Determines if the image represents a category or text to speak
   * 
   * @param imageLoc the location where the image is stored
   * @return true if the image represents a category, false if the image represents text to speak
   */
  public boolean isCategory(String imageLoc){

    // if the image appears in the top level structure, it represents a main category
    if(topLevelCategory.hasImage(imageLoc)){
      return true;
    }
    // otherwise it represents text to speak
    return false;
  } // isCategory(String)

  /**
   * Resets the current category of the AAC back to the default category
   */
  public void reset(){
    currentCategory = topLevelCategory;
  } //reset()

  /**
   * Writes the ACC mappings stored to a file.
   * 
   * @param filename the name of the file to write the AAC mapping to
   */
  public void writeToFile(String filename){

    try {
      // create a new file with given filename
      PrintWriter pen = new PrintWriter(new File(filename));
      
      // for each category on the home screen
      for(String category : topLevelCategory.getImages()){
        if(category!= null){
          // print image and category name
          pen.println(">" + category + " " + topLevelCategory.getText(category));

          // for each image within a category
          for(String loc : categories.get(category).getImages()){
            if(loc != null){
              // print image and associated text
              pen.println(loc + " " + categories.get(category).getText(loc));
            } // if
          } // for
        } // if
      } // for

      pen.close();
    } catch (Exception e) {
      System.err.println("Error: unable to write to file " + filename);
    }
  } // writeToFile (String)

} // class AACMappings