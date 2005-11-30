/* Generated by Together */

package design;

public abstract class TitleBlurbVisitor
{  
   String titleBlurb;
   public void setTitleBlurb(String blurbIn) {this.titleBlurb = blurbIn;}
   public String getTitleBlurb() {return this.titleBlurb;}     
    
   public abstract void visit(BookInfo bookInfo);  
   public abstract void visit(DvdInfo dvdInfo);   
   public abstract void visit(GameInfo gameInfo);
}


public class BookInfo extends AbstractTitleInfo
{  
   private String author;
    
   public BookInfo(String titleName, String author)
   {
       this.setTitleName(titleName);
       this.setAuthor(author);
   }    
   
   public void setAuthor(String authorIn) {this.author = authorIn;}
   public String getAuthor() {return this.author;}   
   
   public void accept(TitleBlurbVisitor titleBlurbVisitor) {titleBlurbVisitor.visit(this);}   
}


/* Generated by Together */

class TestTitleVisitor {            
   public static void main(String[] args) 
   {
       AbstractTitleInfo bladeRunner = new DvdInfo("Blade Runner", "Harrison Ford", '1');  
       AbstractTitleInfo electricSheep = new BookInfo("Do Androids Dream of Electric Sheep?", "Phillip K. Dick");        
       AbstractTitleInfo sheepRaider = new GameInfo("Sheep Raider");        
       
       TitleBlurbVisitor titleLongBlurbVisitor = new TitleLongBlurbVisitor();
       
       System.out.println("Long Blurbs:");     
       bladeRunner.accept(titleLongBlurbVisitor);
       System.out.println("Testing bladeRunner   " + titleLongBlurbVisitor.getTitleBlurb()); 
       electricSheep.accept(titleLongBlurbVisitor);
       System.out.println("Testing electricSheep " + titleLongBlurbVisitor.getTitleBlurb());   
       sheepRaider.accept(titleLongBlurbVisitor);
       System.out.println("Testing sheepRaider   " + titleLongBlurbVisitor.getTitleBlurb());   
       
       TitleBlurbVisitor titleShortBlurbVisitor = new TitleShortBlurbVisitor();
       
       System.out.println("Short Blurbs:");     
       bladeRunner.accept(titleShortBlurbVisitor);
       System.out.println("Testing bladeRunner   " + titleShortBlurbVisitor.getTitleBlurb()); 
       electricSheep.accept(titleLongBlurbVisitor);
       System.out.println("Testing electricSheep " + titleShortBlurbVisitor.getTitleBlurb());   
       sheepRaider.accept(titleShortBlurbVisitor);
       System.out.println("Testing sheepRaider   " + titleShortBlurbVisitor.getTitleBlurb());         
   }
}


