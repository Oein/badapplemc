package kr.oein.badapplemc

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.block.BlockState
import org.bukkit.block.data.Openable
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists


class Worker() {
    private lateinit var world: World
    private lateinit var plgin: Badapplemc

    constructor(world: World, plgin: Badapplemc) : this() {
        this.world = world
        this.plgin = plgin
    }

    var tick = 0
    public fun run() {
        this.frame0()

        Bukkit.getScheduler().runTaskTimer(plgin, { task ->
            tick++;
            val pth = getPth(tick)

            plgin.server.onlinePlayers.forEach { player -> player.sendActionBar(Component.text("Running: ${tick} ${pth.toString()}"))}


            if(!pth.exists()) {
                task.cancel()
                return@runTaskTimer
            }



            val dt = pth.toFile().readText()
            plgin.server.onlinePlayers.forEach { player -> player.sendActionBar(Component.text("Runningexe: ${tick} ${pth.toString()} size: ${dt.length}"))}
            executeFrame(dt)
        }, 0L, 1L)
    }

    private fun getPth(frame: Int): Path {

        return Paths.get(plgin.dataPath.parent.toString(), "resx", "$frame.bin")
    }

    private fun s(x: Int, z: Int, opened: Boolean) {
        val block = this.world.getBlockAt(x, 4, z)
        // trapDoor
        val state: BlockState = block.state
        val openable: Openable = state.blockData as Openable
        openable.isOpen = opened
        state.blockData = openable
        state.update()

        val loc = Location(this.world,x.toDouble(),4.0,z.toDouble())

        if(opened)
        for (online in world.players) {
//            online.playSound(loc, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 16f, 1f)
        }
    }

    private fun executeFrame(frameData: String) {
        // for with 3 steps
        var i = 0
        while(i < frameData.length) {
            val x = frameData[i].toInt()
            val z = frameData[i + 1].toInt()
            val opened = frameData[i + 2].toInt() == 1

            s(x,z,opened)

            i += 3
        }
    }

    private fun frame0() {
        for(i in 0..120) {
            for(j in 0..90) {
                val block = this.world.getBlockAt(i,4,j)
                block.type = Material.IRON_TRAPDOOR
            }
        }
    }
}