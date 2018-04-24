open class BaseModel(open val id: Int)
data class User(val name: String, override val id: Int) : BaseModel(id)
data class Product(val price: Double, override val id: Int) : BaseModel(id)

class Repository<T : BaseModel> {
    private val storage = hashMapOf<Int, T>()
    fun save(t: T) {
        storage[t.id] = t
    }

    fun get(id: Int): T? {
        return storage[id]
    }
}

open class GenericBusiness<T : BaseModel, in R : Repository<T>>(private val repository: R) {
    open fun save(obj: T) {
        if(obj.id != 0){
            repository.save(obj)
        }
    }

    open fun get(id: Int) {
        if(id != 0){
            repository.get(id)
        }
    }
}

class ProductBusiness : GenericBusiness<Product, Repository<Product>>(Repository()){
    override fun save(obj: Product) {
        if(obj.price <= 0) {
            throw IllegalArgumentException("invalid price")
        }
        super.save(obj)
    }
}
