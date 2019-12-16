package blockchain.resources;

import java.security.*;
import java.sql.Struct;
import java.util.ArrayList;

public class Transaction {
    // this is also the hash of the transaction
    public String transactionId;
    // senders address/public key
    public PublicKey sender;
    // recipient address/public key
    public PublicKey recipient;
    // value
    public float value;
    // prevents anyone else from spending funds in our wallet
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    // a rough count of how many transactions have been generated
    private static int sequence = 0;

    //Constructor\\
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // this calculates the transaction hash( which will be used as its Id)
    private String calculateHash() {
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        Float.toString(value) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

}
