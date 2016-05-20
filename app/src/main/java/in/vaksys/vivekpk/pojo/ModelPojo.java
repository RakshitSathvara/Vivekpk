package in.vaksys.vivekpk.pojo;

import java.util.List;

/**
 * Created by Harsh on 17-05-2016.
 */
public class ModelPojo {

    /**
     * error : false
     * result : [{"id":1,"manufacturerName":"Audi","model":"A1","type":"Car","createdAt":"Car","updatedAt":"2016-05-13 03:30:14"},{"id":2,"manufacturerName":"Audi","model":"A2","type":"Car","createdAt":"Car","updatedAt":"2016-05-13 03:30:32"},{"id":3,"manufacturerName":"Audi","model":"A3","type":"Car","createdAt":"Car","updatedAt":"2016-05-13 03:31:05"},{"id":4,"manufacturerName":"BMW","model":"Q3","type":"Car","createdAt":"Car","updatedAt":"2016-05-13 03:31:29"}]
     */

    private boolean error;
    /**
     * id : 1
     * manufacturerName : Audi
     * model : A1
     * type : Car
     * createdAt : Car
     * updatedAt : 2016-05-13 03:30:14
     */

    private List<ResultEntity> result;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        private int id;
        private String manufacturerName;
        private String model;
        private String type;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
