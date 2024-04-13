import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.VideoItemBinding
import com.leperlier.quinquis.lentz.imdb.data.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoAdapter(private val videos: List<Video>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(video.key, 0f)
                }
            })
        }

        fun release() {
            binding.youtubePlayerView.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        holder.release()
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int = videos.size
}
