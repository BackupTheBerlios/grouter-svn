/* Generated by Together */

package design.strategy;


public class BirdClient {
    public static void main(String[] args)
    {
		Bird eagle = new Eagle();
        eagle.doFly();
        eagle.doSing();
        // now change their behaviour...
        eagle.setFlyBehavior(new DoNotFly());
		eagle.setSingBehavior(new DoNotSing());
    }
}
