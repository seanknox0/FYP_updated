package ie.wit.fyp_updated.ui.personal

class Entry {

    var entryID:Int?=null
    var entryTitle:String?=null
    var entryDesc:String?=null

    constructor(entryID:Int, entryTitle:String, entryDes:String){
        this.entryID=entryID
        this.entryTitle=entryTitle
        this.entryDesc=entryDes
    }
}