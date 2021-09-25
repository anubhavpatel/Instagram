package Model

class User {
    fun getUserName(): String {
        return userName
    }

    fun getFullname(): String {
        return fullName
    }
    fun getImage(): String{
        return image
    }

    private var userName: String=""
    private var fullName: String=""
    private var bio: String=""
    private var image: String=""
    private var uid: String=""

    constructor()
    constructor(userName: String,fullName: String, bio: String, image: String,uid: String)
    {
        this.userName = userName
        this.fullName = fullName
        this.bio = bio
        this.image = image
        this.uid = uid
        fun getUsername(): String{
            return userName
        }
        fun setUsername(userName:String){
            this.userName = userName
        }
        fun getFullname(): String{
            return fullName
        }
        fun setFullname(fullName:String){
            this.fullName = fullName
        }
        fun getBio(): String{
            return bio
        }
        fun setBio(bio:String){
            this.bio = bio
        }
        fun getImage(): String{
            return image
        }
        fun setImage(image:String){
            this.image = image
        }
        fun getUid(): String{
            return uid
        }
        fun setUid(uid:String){
            this.uid = uid
        }
    }
}