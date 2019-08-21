package com.xxTFxx.siberianadv.network;

import com.xxTFxx.siberianadv.container.ContainerPortableGenerator;
import com.xxTFxx.siberianadv.render.LineRender;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Mes implements IMessage{

	public int x , y , z;
	
	public Mes(){}

    public Mes(int x , int y , int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

    /*@Override
    public void fromBytes(ByteBuf buf , ByteBuf buf1 , ByteBuf buf2){
        super.fromBytes(buf);
        x = buf.readInt();
        y = buf1.readInt();
        z = buf2.readInt();
    }

    //@Override
    public void toBytes(ByteBuf buf , ByteBuf buf1 , ByteBuf buf2){
        super.toBytes(buf);
        buf.writeInt(x);
        buf1.writeInt(y);
        buf2.writeInt(z);
    }

    @Override
    public void handleClientSide(Mes message, EntityPlayer player){
    	Vec3d vec1 = new Vec3d(x, y, z);
		Vec3d vec3 = new Vec3d(x + 2, y + 2, z + 2);
    	LineRender.drawBoundingBox(vec1, vec1, vec3, false, 0.3F);
    }

    @Override
    public void handleServerSide(Mes message, EntityPlayer player){
    }*/

}
