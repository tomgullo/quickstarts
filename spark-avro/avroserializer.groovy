import org.apache.avro.specific.*
import com.esotericsoftware.kryo.*
import com.esotericsoftware.kryo.io.*
import org.apache.avro.io.*
import org.apache.avro.generic.*
import my.custom.CustomAvroObject
import org.apache.spark.serializer.KryoRegistrator
 
class AvroSerializer<T>; extends Serializer<T> {
 
  def reader
  def writer
  BinaryEncoder encoder
  BinaryDecoder decoder
 
  AvroSerializer(schema) {
    super()
    setAcceptsNull(false)
    reader = new GenericDatumReader(schema)
    writer = new GenericDatumWriter(schema)
  }
 
  void write(Kryo kryo, Output output, T record) {
    encoder = EncoderFactory.get().directBinaryEncoder(output, encoder)
    writer.write(record, encoder)
  }
 
  def read(Kryo kryo, Input input, Class&lt;T&gt; klazz) {
    synchronized(this) {
      decoder = DecoderFactory.get().directBinaryDecoder(input, decoder)
      reader.read(null, decoder)
    }   
  }
}
 
class AvroSpecificRegistrator implements KryoRegistrator {
  @Override
  void registerClasses(Kryo kryo) {
    kryo.register(CustomAvroObject.class, new AvroSerializer(CustomAvroObject.SCHEMA$))
    
  }
}
