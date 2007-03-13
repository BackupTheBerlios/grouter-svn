package org.grouter.domain.entities;

/**
 * @author Georges Polyzois
 */
abstract class EndPointWriter extends EndPoint
{
    Backup backup;


    public Backup getBackup()
    {
        return backup;
    }

    public void setBackup(Backup backup)
    {
        this.backup = backup;
    }

}
