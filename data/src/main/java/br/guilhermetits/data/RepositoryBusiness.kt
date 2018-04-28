package br.guilhermetits.data

open class BaseModel(open val id: Int)
data class User(val name: String, override val id: Int) : BaseModel(id)
data class Product(val price: Double, override val id: Int) : BaseModel(id)

interface Repository<T : BaseModel> {
    fun save(t: T)
    fun get(id: Int): T?
}

class RepositoryImpl<T : BaseModel> : Repository<T> {
    private val storage = hashMapOf<Int, T>()
    override fun save(t: T) {
        storage[t.id] = t
    }

    override fun get(id: Int): T? {
        return storage[id]
    }
}

open class GenericBusiness<T : BaseModel, in R : Repository<T>>(private val repository: R) {
    open fun save(obj: T) {
        if (obj.id == 0) {
            throw IllegalArgumentException("invalid id")
        }

        repository.save(obj)

    }

    open fun get(id: Int) {
        if (id == 0) {
            throw IllegalArgumentException("invalid id")
        }
        repository.get(id)
    }
}

class ProductBusiness(repository: Repository<Product>) : GenericBusiness<Product, Repository<Product>>(repository) {
    override fun save(obj: Product) {
        if (obj.price <= 0) {
            throw IllegalArgumentException("invalid price")
        }
        super.save(obj)
    }
}