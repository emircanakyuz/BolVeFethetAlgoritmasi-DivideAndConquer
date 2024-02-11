import java.util.ArrayList;
import java.util.List;

public class BolVeFethetAlgoritması {

    public static List<int[]> getSkyline(int[][] binalar)
    {
        if (binalar.length == 0) // Dizi boş ise boş bir dizi döndürür.
        {
            return new ArrayList<>();
        }

        return bolVeFethet(binalar, 0, binalar.length - 1);
    }

    private static List<int[]> bolVeFethet(int[][] binalar, int baslangic, int son)
    // Böl ve Fethet algoritması bolVeFethet(int[][] binalar, int baslangic, int son) adlı bu metotta gerçekleştirilir.
    // Bu metot, verilen bina dizisini belirli bir aralıkta (başlangıç ve son indeksler arasında) böler ve
    // alt problemleri çözmek için özyinelemeli olarak kendisini çağırır. Ardından, alt problemlerin çözümleri birleştirilir.
    {
        List<int[]> sonuc = new ArrayList<>();

        if (baslangic == son)
        {
            int[] nokta1 = { binalar[baslangic][0], binalar[baslangic][2] };
            int[] nokta2 = { binalar[baslangic][1], 0 };
            sonuc.add(nokta1);
            sonuc.add(nokta2);
            return sonuc;
        }

        int orta = (baslangic + son) / 2;
        List<int[]> solSkyline = bolVeFethet(binalar, baslangic, orta);
        List<int[]> sagSkyline = bolVeFethet(binalar, orta + 1, son);
        sonuc = birlestirSkylines(solSkyline, sagSkyline);

        return sonuc;
    }

    private static List<int[]> birlestirSkylines(List<int[]> solSkyline, List<int[]> sagSkyline)
    // Bu metod, iki ayrı skyline listesini birleştirir ve tek bir skyline listesi oluşturur.
    // Bu birleştirme işlemi sırasında, bina yapılarının başlangıç noktaları arasında birleştirme gerçekleştirilirken
    // yüksekliklerin doğru bir şekilde birleştirilmesine dikkat edilir.
    {
        List<int[]> birlestirSkyline = new ArrayList<>();
        int solYukseklik = 0 ;
        int sagYukseklik = 0;
        int i = 0;
        int j = 0;

        while (i < solSkyline.size() && j < sagSkyline.size()) {
            int[] nokta1 = solSkyline.get(i);
            int[] nokta2 = sagSkyline.get(j);
            int x;
            int yukseklik;

            if (nokta1[0] < nokta2[0]) {
                x = nokta1[0];
                solYukseklik = nokta1[1];
                yukseklik = Math.max(solYukseklik, sagYukseklik);
                i++;
            } else if (nokta1[0] > nokta2[0]) {
                x = nokta2[0];
                sagYukseklik = nokta2[1];
                yukseklik = Math.max(solYukseklik, sagYukseklik);
                j++;
            } else {
                x = nokta1[0];
                solYukseklik = nokta1[1];
                sagYukseklik = nokta2[1];
                yukseklik = Math.max(solYukseklik, sagYukseklik);
                i++;
                j++;
            }

            if (birlestirSkyline.isEmpty() || yukseklik != birlestirSkyline.get(birlestirSkyline.size() - 1)[1]) {
                birlestirSkyline.add(new int[] { x, yukseklik });
            }
        }

        while (i < solSkyline.size()) {
            birlestirSkyline.add(solSkyline.get(i++));
        }

        while (j < sagSkyline.size()) {
            birlestirSkyline.add(sagSkyline.get(j++));
        }
        return birlestirSkyline;
    }

    public static void main(String[] args) {
        int[][] binalar1 = {
                {2, 9, 10},
                {3, 7, 15},
                {5, 12, 12},
                {15, 20, 10},
                {19, 24, 8}
        };

        int[][] binalar2 = {
                {5, 6, 11},
                {1, 9, 7},
                {5, 10, 12},
                {10, 25, 19},
                {19, 24, 10}
        };

        int[][] binalar3 = {
                {1, 3, 3},
                {2, 4, 4},
                {5, 8, 2},
                {6, 7, 4},
                {8, 9, 4}
        };

        System.out.println("Birinci görüntünün anahtar noktaları: ");

        List<int[]> skyline1 = getSkyline(binalar1);

        for (int[] nokta : skyline1) {
            System.out.println("(" + nokta[0] + ", " + nokta[1] + ")");
        }

        System.out.println("\n");
        System.out.println("İkinci görüntünün anahtar noktaları: ");

        List<int[]> skyline2 = getSkyline(binalar2);

        for (int[] nokta : skyline2) {
            System.out.println("(" + nokta[0] + ", " + nokta[1] + ")");
        }

        System.out.println("\n");
        System.out.println("Üçüncü görüntünün anahtar noktaları: ");

        List<int[]> skyline3 = getSkyline(binalar3);

        for (int[] nokta : skyline3) {
            System.out.println("(" + nokta[0] + ", " + nokta[1] + ")");
        }
    }
}



/*
ZAMAN KARMAŞIKLIĞI:
Yukarıdaki algoritmanın zaman karmaşıklığı "Merge Sort" Algoritması ile aynı zaman karmaşıklığına sahiptir.
Bu özyinelemeli (recursive) bir fonksiyon olduğu için sürekli kendini çağırarak hep diziyi ikiye bölüyor.
Böylelikle en fazla log2(n) kere bölme işlemi yapılmış oluyor.
Bu algoritmanın zaman karmaşıklığı büyük O notasyonunda O(n*log(n)) oluyor.
*/
