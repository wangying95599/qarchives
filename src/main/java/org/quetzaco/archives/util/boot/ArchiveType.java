package org.quetzaco.archives.util.boot;


public interface ArchiveType {
    enum DocumentType implements ArchiveType {
        WS("文书档案","WS"),
        RS("人事档案","RS"),
        SW("实物档案","SW"),
        HT("合同档案","HT"),
        KW("刊物档案","KW"),
        YX("印信档案","YX"),
        FC("房产档案","FC"),
        ZJ("证件档案","ZJ");
        private String value;
        private String alias;

        private DocumentType(String value,String alias) {
            this.value = value;
            this.alias = alias;
        }

        public String getValue() {
            return this.value;
        }
        public String getAlias(){
            return this.alias;
        }
        public static boolean isDocumentType(String alias){
             boolean result = false;
             for(DocumentType documentType : DocumentType.values()){
                if(documentType.getAlias().equals(alias)){
                    result = true;
                    break;
                }
             }
             return result;
        }
    }

    enum RecordType implements ArchiveType {
        JJ("基建档案","JJ"),
        SX("声像档案","SX"),
        SJJC("审计监察档案","SJJC"),
        AJ("案件档案","AJ"),
        SG("生产安全事故档案","SG"),
        XX("信息化建设档案","XX"),
        ZTB("招投标档案","ZTB"),
        KJ("会计档案","KJ"),
        DJ("单机档案","DJ");
        private String value;
        private String alias;

        private RecordType(String value,String alias) {
            this.value = value;
            this.alias = alias;
        }
        

        public String getValue() {
            return this.value;
        }
        public String getAlias(){
            return this.alias;
        }
        public static boolean isRecordType(String alias){
            boolean result = false;
            for(RecordType recordType : RecordType.values()){
                if(recordType.getAlias().equals(alias)){
                    result = true;
                    break;
                }
            }
            return result;
        }
    }
}
