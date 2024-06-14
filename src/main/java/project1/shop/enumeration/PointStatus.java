package project1.shop.enumeration;

public enum PointStatus {

    PURCHASE_DONE("구매 적립"),
    PURCHASE_USE("구매 사용"),
    PURCHASE_CANCEL("구매 취소"),
    REVIEW("리뷰")
    ;



    // 문자열을 저장할 필드
    private String pointStatus;

    // 생성자 (싱글톤)
    private PointStatus(String pointStatus){
        this.pointStatus = pointStatus;
    }

    // Getter (해당 상수의 설명 문자열을 반환)
    public String getPointStatus() {
        return pointStatus;
    }

    // 상수 설명 문자열을 받아와서 상수 저장
    public static PointStatus fromString(String text) {
        for (PointStatus p : PointStatus.values()) {
            if (p.getPointStatus().equalsIgnoreCase(text)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
