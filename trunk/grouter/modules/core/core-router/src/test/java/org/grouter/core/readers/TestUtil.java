package org.grouter.core.readers;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class TestUtil
{

    private static final int DEFAULT_PORT = 12345;

    public static File getBaseDir()
    {
        // check Maven system prop first and use if set
        String basedir = System.getProperty("basedir");
        if (basedir != null)
        {
            return new File(basedir);
        } else
        {
            return new File(".");
        }
    }

    /**
     * Attempts to find a free port or fallback to a default
     *
     * @throws java.io.IOException
     * @throws java.io.IOException
     */
    public static int findFreePort() throws IOException
    {
        int port = -1;
        ServerSocket tmpSocket = null;
        // first try the default port
        try
        {
            tmpSocket = new ServerSocket(DEFAULT_PORT);

            port = DEFAULT_PORT;
        } catch (IOException e)
        {
            System.out.println("Failed to use default port");
            // didn't work, try to find one dynamically
            try
            {
                int attempts = 0;

                while (port < 1024 && attempts < 1000)
                {
                    attempts++;

                    tmpSocket = new ServerSocket(0);

                    port = tmpSocket.getLocalPort();
                }

            } catch (IOException e1)
            {
                throw new IOException("Failed to find a port to use for testing: "
                        + e1.getMessage());
            }
        } finally
        {
            if (tmpSocket != null)
            {
                try
                {
                    tmpSocket.close();
                } catch (IOException e)
                {
                    // ignore
                }
                tmpSocket = null;
            }
        }

        return port;
    }

    public static String[] getHostAddresses() throws Exception
    {
        Enumeration nifs = NetworkInterface.getNetworkInterfaces();

        List hostIps = new ArrayList();
        while (nifs.hasMoreElements())
        {
            NetworkInterface nif = (NetworkInterface) nifs.nextElement();
            Enumeration ips = nif.getInetAddresses();

            while (ips.hasMoreElements())
            {
                InetAddress ip = (InetAddress) ips.nextElement();
                hostIps.add(ip.getHostAddress());
            }
        }

        return (String[]) hostIps.toArray(new String[0]);
    }



}

