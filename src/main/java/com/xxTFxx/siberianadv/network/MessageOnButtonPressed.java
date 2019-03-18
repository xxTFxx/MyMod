package com.xxTFxx.siberianadv.network;

import com.xxTFxx.siberianadv.container.ContainerPortableGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class MessageOnButtonPressed extends MessageXYZ<MessageOnButtonPressed>{

	private int id;

	public MessageOnButtonPressed(){}

    public MessageOnButtonPressed(TileEntityPortableGenerator te, int id){
        super(te);
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        super.fromBytes(buf);
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        super.toBytes(buf);
        buf.writeInt(id);
    }

    @Override
    public void handleClientSide(MessageOnButtonPressed message, EntityPlayer player){

    }

    @Override
    public void handleServerSide(MessageOnButtonPressed message, EntityPlayer player){
    	ContainerPortableGenerator container = (ContainerPortableGenerator)player.openContainer;
        TileEntity te = container.tile;
        if(te instanceof TileEntityPortableGenerator) {
            ((TileEntityPortableGenerator)te).onGuiButtonPress(message.id);
        }
    }

}