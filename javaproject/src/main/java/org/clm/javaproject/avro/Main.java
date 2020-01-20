package org.clm.javaproject.avro;


import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class Main {
    User user1 = User.newBuilder()
            .setName("Alyssa")
            .setFavoriteNumber(256)
            .build();
    User user2 = new User("Ben", 7, "red");
    User user3 = User.newBuilder()
            .setName("Charlie")
            .setFavoriteColor("blue")
            .setFavoriteNumber(null)
            .build();

    // Serialize user1, user2 and user3 to disk
    DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
    DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
    
   /*  dataFileWriter.create(user1.getSchema(), new File("users.avro"));
    dataFileWriter.append(user1);
    dataFileWriter.append(user2);
    dataFileWriter.append(user3);
    dataFileWriter.close();*/


}
