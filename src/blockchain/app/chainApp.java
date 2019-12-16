package blockchain.app;

import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import blockchain.resources.Block;
import blockchain.resources.StringUtil;
import blockchain.resources.Transaction;
import blockchain.resources.Wallet;
import com.google.gson.GsonBuilder;
import org.bouncycastle.*;

import java.util.ArrayList;

public class chainApp {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args) {
        /*
        First Block
         */
        Block genesisBlock = new Block(
                "Hi im the first block",
                "0",
                "Im sending 1,000,000,000 Dollars"
        );
        blockchain.add(genesisBlock);
        System.out.println(
                "Trying to mine block 1 ...."
        );
        blockchain.get(0).mineBlock(difficulty);
        /*
        Second Block with first block's hash
         */
        Block secondBlock = new Block(
                "Yo im the second block",
                genesisBlock.hash,
                "Im saving 30,000 in my account"
        );
        blockchain.add(secondBlock);
        System.out.println(
                "Trying to mine block 2 ....."
        );
        blockchain.get(1).mineBlock(difficulty);
        /*
        Third Block with the second block's hash
         */
        Block thirdBlock = new Block(
                "Hey I'm the third block",
                secondBlock.hash,
                "Im withdrawing 12,000 to pay for college"
        );
        blockchain.add(thirdBlock);
        System.out.println(
                "Trying to mine block 3 ...."
        );
        blockchain.get(2).mineBlock(difficulty);

        String blockchainJson = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(blockchain);
        System.out.println(blockchainJson);

        updtatedSolution();

    }

    private static void updtatedSolution() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        walletA = new Wallet();
        walletB = new Wallet();

        System.out.println(" private and Public keys");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        //
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5 , null);
        transaction.generateSignature(walletA.privateKey);
        //
        System.out.println(" Is signature verified ?");
        System.out.println(transaction.verifySignature());
    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        /*
        Check hashes by looping through all of them and
        comparing the hash
         */
        for (int i = 1; i < blockchain.size(); i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //comparing hashes
            if (!currentBlock.hash
                    .equals(currentBlock.
                            calculatehash())){
                System.out.println("Current Hashes not equal");
                return false;
            }
            // comparing old hashes to new hashes
            if (!previousBlock.hash
                    .equals(currentBlock.
                            previousHash)){
                System.out.println("Previous hash is not equal");
                return false;

            }
            // check if hash is solved
            if (!currentBlock.hash.substring(0,difficulty).equals(hashTarget)){
                System.out.println("This block hasn't been mined");
                return false;
            }

        }

        /*
        Only return true if the checks pass.
         */
        return true;
    }

}

