package com.osk.exercises;

/**
Постановка задачи:
Есть объект интерфейса Reader у которого есть метод int read(byte[] arr). Метод читает файл и прочитанные байты пишет
в массив arr и возвращает количество прочитанных байт. Максимум он может прочитать 1024 байта. Если байтов в файле
осталось меньше, то прочитает и вернёт то количество, которое осталось. В случае какой-либо ошибки вернёт -1.

На его основе написать метод int read(byte[] arr, int num) который прочитает то количество байт, которое ему передали
в num. И запишет их соответсвенно в массив.
 */

public class ByteReader {

    byte[] buf;
    int cur_idx;

    Reader reader;

    public ByteReader(Reader reader) {
        this.reader = reader;
    }

    public static void main(String[] args) {
        // arr[5] num = 3
        // arr[4000] num = 4000
    }

    public int read(byte[] arr, int num) {
        int res = 0;
        if (buf == null) {
            buf = new byte[num];
            res = reader.read(buf);
        } else if (cur_idx > 0) {
            res = buf.length - cur_idx;
        }

        if (res == num) {
            System.arraycopy(buf, 0, arr, 0, num);
            buf = null;
            return res;
        } else if (res > num) {
            System.arraycopy(buf, 0, arr, 0, num);
            cur_idx += num;
            return num;
        } else {
            int tmp_res = res;
            System.arraycopy(buf, 0, arr, 0, tmp_res);
            buf = new byte[num - res];
            res = reader.read(buf);
            if (num == tmp_res + res) {
                System.arraycopy(buf, 0, arr, tmp_res + 1, res);
            }
            return res;
        }
    }
}


interface Reader {
    int read(byte[] arr);
}