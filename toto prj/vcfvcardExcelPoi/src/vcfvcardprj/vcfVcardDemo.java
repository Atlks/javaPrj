package vcfvcardprj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.csvreader.CsvReader;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Nickname;

public class vcfVcardDemo {

	public static void main(String[] args) throws IOException {
		
		String filePath="C:\\Users\\Administrator\\Documents\\ccs duba.csv";
		  // ����CSV������
	//	 new CsvReader
        CsvReader csvReader = new CsvReader(  new FileInputStream(new File(filePath)) , Charset.forName("gbk"));

        List li=Lists.newArrayList();
        // ����ͷ
      //  csvReader.readHeaders();
        while (csvReader.readRecord()){
            // ��һ����
            System.out.println(csvReader.getRawRecord());
            // �����е�ĳһ��
            System.out.println(csvReader.get(0));
      //      System.out.println(csvReader.get("Link"));
            VCard vcard = new VCard(); 
            vcard.setFormattedName(csvReader.get(1));
            vcard.addTelephoneNumber(csvReader.get(0), TelephoneType.CELL);
   //         vcard.addNickname( new Nickname().set csvReader.get(1));
         
          String str = Ezvcard.write(vcard).version(VCardVersion.V3_0).go();
          li.add(str);
        }
        
        String s=Joiner.on("\r\n").join(li);
        FileUtils.write(new File("d:\\ccs_duba.vcf"),s,"utf8");
 System.out.println(s);
		
	}
}
