package viewerPackage;

import java.util.ArrayList;

public class MemberData {

    //FIELDS
    private ArrayList<String> memberDatas = new ArrayList<>();
    private boolean modifiying;

    //CONSTRUCTOR
    MemberData() { }

    //METHODS
    public void addMemberData(String memberData) {
        if(memberDatas.contains(memberData)) return;
        memberDatas.add(memberData);
    }

    public void setModifiying(boolean modifiying) { this.modifiying = modifiying; }
    public boolean isModifiying() { return modifiying; }

    public void modifyMemberData(String memberData) {
        if(isModifiying()) {
            memberDatas.clear();
            setModifiying(false);
        }
        addMemberData(memberData);
    }

    public ArrayList<String> getMemberDatas() {
        if(memberDatas.isEmpty()) return null;
        return memberDatas;
    }

}
