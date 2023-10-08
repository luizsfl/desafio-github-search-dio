package br.com.igorbag.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.databinding.RepositoryItemBinding
import br.com.igorbag.githubsearch.domain.Repository
import com.bumptech.glide.Glide

class RepositoryAdapter(private val repositories: List<Repository>) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var carItemLister: (Repository) -> Unit = {}
    var btnShareLister: (Repository) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepositoryItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(repositories[position])

        holder.itemView.setOnClickListener {
            carItemLister?.invoke(repositories[position])
        }

        holder.binding.ivFavorite.setOnClickListener {
            btnShareLister?.invoke(repositories[position])
        }

    }

    override fun getItemCount(): Int = repositories.size

    class ViewHolder(val binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repository) {
            binding.tvName.text = "Nome: ${item.name}"
            binding.tvStar.text = "Estrela: ${item.stargazersCount}"
            binding.tvFork.text = "Fork: ${item.forks_count}"
            binding.tvSee.text  = "Assistindo: ${item.watchers_count}"
        }
    }

}


