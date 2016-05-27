package in.vaksys.vivekpk.pojo;

import java.util.List;

/**
 * Created by Harsh on 27-05-2016.
 */
public class DatePojo {

    private List<DateEntity> result;

    public static class DateEntity {
        private String mDate;

        private String id;

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
