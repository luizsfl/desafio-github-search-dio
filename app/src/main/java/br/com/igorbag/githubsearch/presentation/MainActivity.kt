package br.com.igorbag.githubsearch.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.core.ViewState
import br.com.igorbag.githubsearch.data.remote.GitHubService
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.domain.model.Repository
import br.com.igorbag.githubsearch.presentation.adapter.RepositoryAdapter
import com.bumptech.glide.Glide
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.ext.android.viewModel


//https://square.github.io/retrofit/
//URL_BASE da API do  GitHub= https://api.github.com/

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var gitHubService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showUserName()
        setupRetrofit()
        getAllReposByUserName(binding.etNomeUsuario.text.toString())
        setupListeners()
        setupView()
        setupObserv()
    }

    private fun setupObserv() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewState.SetLoading ->  showLoading(state.isLoading)
                is ViewState.SetRepositoryList -> setupAdapter(state.listRepository)
                is ViewState.LoadFailure -> showErro(state.messageError)
            }
        }
    }

    fun setupView(){
        getSupportActionBar()?.hide();
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
           viewModel.getAllRepository(name)
        }
    }

    fun setupAdapter(list: List<Repository>) {
        val repositoryAdapter = RepositoryAdapter(list)

        updateImg(list)

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

    fun updateImg(list: List<Repository>){
        if (list.size>0){
            Glide.with(binding.root.context)
                .load(list[0].owner.avatarURL).into(binding.imgPerfil)
        }
    }

    fun showLoading(loading:Boolean){
        binding.progressBar.isVisible = loading
    }

    fun showErro(MessengerErro:String){
        Toast.makeText(this@MainActivity, R.string.response_erro, Toast.LENGTH_LONG)
            .show()
    }

}