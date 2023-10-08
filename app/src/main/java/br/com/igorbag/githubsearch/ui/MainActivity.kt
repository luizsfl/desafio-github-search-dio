package br.com.igorbag.githubsearch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.domain.Repository
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://square.github.io/retrofit/
//URL_BASE da API do  GitHub= https://api.github.com/

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var gitHubService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showUserName()
        setupRetrofit()
        getAllReposByUserName(binding.etNomeUsuario.text.toString())
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnConfirmar.setOnClickListener {
            val nome = binding.etNomeUsuario.text.toString()
            getAllReposByUserName(nome)
            saveUserLocal(nome)
        }
    }

    private fun saveUserLocal(nome:String) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)?:return
        with(sharedPref.edit()){
            putString(getString(R.string.saved_nome), nome)
            apply()
        }
    }

    private fun showUserName() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val nome = sharedPref.getString(getString(R.string.saved_nome),"")
        binding.etNomeUsuario.setText(nome)
    }

    fun setupRetrofit() {
        gitHubService = Retrofit.Builder()
            .baseUrl(getString(R.string.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }

    fun getAllReposByUserName(name:String) {

        if(name.isNotEmpty()) {
            gitHubService.getAllRepositoriesByUser(name)
                .enqueue(object : Callback<List<Repository>> {
                    override fun onResponse(
                        call: Call<List<Repository>>,
                        response: Response<List<Repository>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                setupAdapter(it)
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                R.string.response_erro,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                        Toast.makeText(this@MainActivity, R.string.response_erro, Toast.LENGTH_LONG)
                            .show()
                    }

                })
        }
    }

    fun setupAdapter(list: List<Repository>) {
        val repositoryAdapter = RepositoryAdapter(list)

        repositoryAdapter.btnShareLister = { repository ->
            shareRepositoryLink(repository.htmlUrl)
        }

        repositoryAdapter.carItemLister = { repository ->
            openBrowser(repository.htmlUrl)
        }

        binding.rvListaRepositories.adapter = repositoryAdapter

    }

    fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}