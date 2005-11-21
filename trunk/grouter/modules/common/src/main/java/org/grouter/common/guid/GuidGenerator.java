package org.grouter.common.guid;

import java.net.InetAddress;
import org.apache.log4j.Logger;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Generator for <u>g</u>lobally <u>u</u>nique <u>id</u>dentifiers (GUID:s). A
 * GUID is a 32 character {@link java.lang.String} guaranteed to be unique.
 *
 * A GUID has to be unique in time and space. This implementation uses time,
 * random number, hashcode and inetaddress to conform to this.
 *
 * The rmi implementation of a uuid does not contain the space information needed.
 *
 * @author Georges Polyzois
 */
public class GuidGenerator
{
  private static Logger logger = Logger.getLogger(GuidGenerator.class);
  private static GuidGenerator instance = new GuidGenerator();
  private static final String ALGORITHM = "SHA1PRNG";
  private static final int EIGHTCHARLENGTH = 8;
  private static final int FOURCHARLENGTH = 4;
  private Random seeder;
  private String midValue;
  private InetAddress inetAddress;

  /**
   * Setts the seeder and inetaddress.
   */
  private GuidGenerator()
  {
    try
    {
      seeder = new Random(); //SecureRandom.getInstance(ALGORITHM);
      inetAddress = InetAddress.getLocalHost();
    }/*
    catch (NoSuchAlgorithmException ex)
    {
      logger.error("Failed initialising generator", ex);
    }*/
    catch (UnknownHostException ex)
    {
      logger.error("Failed initialising generator", ex);
    }
  }

  /**
   * Get instance for this singelton.
   * @return instance.
   */
  public static GuidGenerator getInstance()
  {
    return instance;
  }

  /**
   * Intetaddress in hexformat as string.
   *
   * @return String
   */
  private String getInetAddress()
  {
    byte[] inetAddressByteArr = inetAddress.getAddress();
    String inetAddressStr = padToHexFormat(getInt(inetAddressByteArr), EIGHTCHARLENGTH);
    return inetAddressStr;
  }

  /**
   * int from byte[].
   *
   * @param abyte0 byte[]
   * @return int
   */
  private int getInt(byte[] abyte0)
  {
    int i = 0;
    int j = 24;
    for (int k = 0; j >= 0; k++) {
      int l = abyte0[k] & 0xff;
      i += (l << j);
      j -= 8;
    }
    return i;
  }

  /**
   * Convert to hex and pad wiht j
   *
   * @param toHexvalue int value which is padded and converted to hex value
   * @param pad int padded up to this value
   * @return String
   */
  private String padToHexFormat(int toHexvalue, int pad)
  {
    String s = Integer.toHexString(toHexvalue);
    return padToHexformat(s, pad) + s;
  }

  /**
   * If string shorter than pad value then pad with '0' up to pad value.
   *
   * @param astring String
   * @param pad int
   * @return String
   */
  private String padToHexformat(String astring, int pad)
  {
    StringBuffer stringbuffer = new StringBuffer();
    if (astring.length() < pad) {
      for (int j = 0; j < (pad - astring.length()); j++) {
        stringbuffer.append("0");
      }
    }
    return stringbuffer.toString();
  }

  /**
   * Create and return a hashcode.
   * @return String
   */
  private String getHashCode()
  {
    return padToHexFormat(this.hashCode(), EIGHTCHARLENGTH);
  }


  /**
   * Generate current time in hex format.
   * @return String
   */
  private String getTime()
  {
    long currentTimeMs = System.currentTimeMillis();
    int i = (int) currentTimeMs & 0xffffffff;
    String time = padToHexFormat(i, EIGHTCHARLENGTH);
    return time;
  }

  /**
   * Random number generated. Synchronized when getting next random
   * int.
   *
   * @return String
   */
  private String getRandom()
  {
      int j;
      synchronized (GuidGenerator.class)
      {
          j = seeder.nextInt();
      }
      String random = padToHexFormat(j, EIGHTCHARLENGTH);
      return random;
  }


  /**
   * Helper.
   * @return String
   */
  private String createGUID( boolean useSeperator )
  {
    StringBuffer buff = new StringBuffer();
    buff.append(getTime());
    if ( useSeperator )
    {
      buff.append( "-" );
    }
    buff.append(getInetAddress());
    if ( useSeperator )
    {
      buff.append( "-" );
    }
    buff.append(getHashCode());
    if ( useSeperator )
    {
      buff.append( "-" );
    }
    buff.append(getRandom());
    return buff.toString();
  }

  /**
   * Gets a GUID.
   *
   * @param useSeperator boolean seperates the guid into 4 categories with seperator.
   * @return String
   */
  public String getGUID( boolean useSeperator )
  {
    return createGUID( useSeperator );
  }

  /**
   * Gets a GUID, but doesn't use a separator.
   * @return String
   */
  public String getGUID()
  {
    return createGUID(false);
  }
}
