import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class Compressor {
    private String method;

    public Compressor(String method) {
        this.method = method;
    }

    private static long compressGzip(String data) throws IOException {
        Path target = Paths.get("compression_tmp.gz");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressBzip2(String data) throws IOException {
        Path target = Paths.get("compression_tmp.bz2");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        BZip2CompressorOutputStream gos = new BZip2CompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressLzma(String data) throws IOException {
        Path target = Paths.get("compression_tmp.lzma");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        OutputStream fout = Files.newOutputStream(target);
        BufferedOutputStream out = new BufferedOutputStream(fout);
        LZMACompressorOutputStream lzOut = new LZMACompressorOutputStream(out);
        lzOut.write(data.getBytes(), 0, data.length());
        lzOut.flush();
        lzOut.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressDeflate(String data) throws IOException {
        Path target = Paths.get("compression_tmp.dfl");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        DeflateCompressorOutputStream gos = new DeflateCompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressLZ4(String data) throws IOException {
        Path target = Paths.get("compression_tmp.lz4");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        FramedLZ4CompressorOutputStream gos = new FramedLZ4CompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressSnappy(String data) throws IOException {
        Path target = Paths.get("compression_tmp.sz");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        FramedSnappyCompressorOutputStream gos = new FramedSnappyCompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressXZ(String data) throws IOException {
        Path target = Paths.get("compression_tmp.xz");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        XZCompressorOutputStream gos = new XZCompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    private static long compressZstd(String data) throws IOException {
        Path target = Paths.get("compression_tmp.z");
        File file = new File(String.valueOf(target)); // variable to see size of the target file after compression
        long bits;
        ZstdCompressorOutputStream gos = new ZstdCompressorOutputStream(new FileOutputStream(target.toFile()));
        gos.write(data.getBytes(), 0, data.length());
        gos.flush();
        gos.close();
        bits = file.length();
        file.delete();
        return bits;
    }

    public long compression(String str) throws IOException {
        long bits = 0;
        switch (method){
            case "gzip":    bits = compressGzip(str);    break;
            case "bzip2":   bits = compressBzip2(str);   break;
            case "lzma":    bits = compressLzma(str);    break;
            case "deflate": bits = compressDeflate(str); break;
            case "lz4":     bits = compressLZ4(str);     break;
            case "snappy":  bits = compressSnappy(str);  break;
            case "xz":      bits = compressXZ(str);      break;
            case "zstd":    bits = compressZstd(str);    break;
        }
        return bits;
    }
}
