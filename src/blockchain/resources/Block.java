package blockchain.resources;

import java.beans.IntrospectionException;
import java.util.Date;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Block {
    /*
    Hash value that will be encrypted
     */
    public String hash;
    /*
    Hash value of the previous block.
     */
    public String previousHash;
    /*
    Private data value that contains information
    stored inside of the Block.
     */
    private String data;
    /*
    TimeStamp that reflects the date & time it was created.
     */
    private long timeStamp;
    /*
    Randomly generated value
     */
    private int nonce;
    /*
    Secret information that could be money sent or recieved
     */
    private final String secretInformation;

    /**
     *
     * This will construct a single Block in the chain that
     * will have a private string as well as other information.
     *
     */
    public Block(String data, String previousHash, String secretInformation){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculatehash();
        this.secretInformation = secretInformation;

    }

    /**
     * Calculating the hash so it will be passed through the StringUtil
     * which will apply the SHA256 algorithm to it and return it. Making
     * it secure in transfer.
     */
    public String calculatehash(){
        String calculatehash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatehash;
    }

    /**
     * We mine blocks here.
     */
    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0,difficulty).equals(target)){
            nonce ++;
            hash = calculatehash();
        }
        System.out.println("Block Mined !!! : " + hash);
    }

}
